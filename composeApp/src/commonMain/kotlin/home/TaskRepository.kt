package home

import co.touchlab.kermit.Logger
import data.NetworkService
import data.ResultWrapper
import data.model.Task
import database.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class TaskRepository(
    private val networkService: NetworkService,
    // Pass in the DAO as needed for saving/retrieving from the local database
    private val taskDao: TaskDao
) {

    suspend fun getTasksByUserId(userId: Int): Flow<List<Task>> = flow {
        val result = networkService.getTasksForUser(userId)
        Logger.w("LogTASKs") { "RESULT FROM NETWORK: $result" }

        when (result) {
            is ResultWrapper.Success -> {
                Logger.w("LogTASKs") { "Updating DB with fresh tasks: ${result.value}" }
                result.value.forEach { taskDao.upsertTask(it) }
                emit(result.value)
            }
            is ResultWrapper.Error -> {
                Logger.w("LogTASKs") { "Network error: $result" }
            }
        }
    }

    // Function to register a user. Checks network response and saves to local storage if successful.
    suspend fun createTask(task: Task): Flow<Task> = flow {
        // Send the network request to register the user
        // Emit the response to ViewModel, handling success or error
        Logger.w("LogTASKs"){"creating Tasks TaskRepo"}
        when (val response = networkService.addTasks(task)) {
            is ResultWrapper.Success -> {
                println(response.value)
                saveTaskLocally(response.value)
                emit(response.value)
            }
            is ResultWrapper.Error -> {
                Logger.w("LogTASKs"){"ERROR in TaskRepo for create Task"}
                println(response)
            }
        }
    }

    suspend fun updateTask(task: Task): Flow<Task> = flow {
        Logger.w("LogTASKs") { "Updating task in TaskRepo: $task" }

        when (val response = networkService.updateTask(task)) {
            is ResultWrapper.Success -> {
                saveTaskLocally(response.value) // Save updated task in local DB
                emit(response.value) // Emit updated task
            }
            is ResultWrapper.Error -> {
                Logger.w("LogTASKs") { "ERROR in TaskRepo for update Task" }
            }
        }
    }


    suspend fun deleteTask(task: Task): Flow<Boolean> = flow {
        Logger.w("LogTASKs") { "Deleting task with ID: ${task.id}" }

        when (val response = networkService.deleteTask(task.id)) {
            is ResultWrapper.Success -> {
                removeTaskLocally(task) // Delete task from local DB
                emit(true) // Emit success status
            }
            is ResultWrapper.Error -> {
                Logger.w("LogTASKs") { "ERROR in TaskRepo for delete Task" }
                emit(false) // Emit failure status
            }
        }
    }

    // Removes task from the local Room database
    private suspend fun removeTaskLocally(task: Task) {
        Logger.w("LogTASKs") { "Deleting task locally with ID: ${task.id}" }
        taskDao.deleteTask(task)
    }

                                          // Saves user data locally in the Room database
    private suspend fun saveTaskLocally(task: Task) {
        Logger.w("LogTASKs"){"saving tasks locally"}
        taskDao.upsertTask(task)
    }
}

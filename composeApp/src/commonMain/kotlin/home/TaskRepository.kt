package home

import co.touchlab.kermit.Logger
import data.NetworkService
import data.ResultWrapper
import data.model.Task
import database.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// Task repository responsible for calling data from network service or DB
class TaskRepository(
    private val networkService: NetworkService,
    private val taskDao: TaskDao
) {
    private val logTag = "TaskRepository"

    suspend fun getTasksByUserId(userId: Int): Flow<List<Task>> = flow {
        Logger.w { "$logTag: Fetching tasks for user $userId" }

        when (val result = networkService.getTasksForUser(userId)) {
            is ResultWrapper.Success -> {
                Logger.w { "$logTag: Fetched ${result.value.size} tasks from network, updating local DB" }
                result.value.forEach { task ->
                    taskDao.upsertTask(task)
                }
                emit(result.value)
            }
            is ResultWrapper.Error -> {
                Logger.e { "$logTag: Failed to fetch tasks: $result" }
                emit(emptyList()) // Emit an empty list on error to avoid unhandled cases
            }
        }
    }

    suspend fun createTask(task: Task): Flow<Task> = flow {
        Logger.w { "$logTag: Creating task: $task" }
        handleNetworkResult(
            call = { networkService.addTask(task) },
            onSuccess = { saveTaskLocally(it) },
            onError = { Logger.e { "$logTag: Failed to create task: $it" } }
        )?.let { emit(it) }
    }

    suspend fun updateTask(task: Task): Flow<Task> = flow {
        Logger.w { "$logTag: Updating task: $task" }
        handleNetworkResult(
            call = { networkService.updateTask(task) },
            onSuccess = { saveTaskLocally(it) },
            onError = { Logger.e { "$logTag: Failed to update task: $it" } }
        )?.let { emit(it) }
    }

    suspend fun deleteTask(task: Task): Flow<Boolean> = flow {
        Logger.w { "$logTag: Deleting task with ID: ${task.id}" }
        handleNetworkResult(
            call = { networkService.deleteTask(task.id) },
            onSuccess = {
                removeTaskLocally(task)
                emit(true)
            },
            onError = {
                Logger.e { "$logTag: Failed to delete task: $it" }
                emit(false)
            }
        )
    }

    // Handles common network call logic to avoid repetition
    private suspend fun <T> handleNetworkResult(
        call: suspend () -> ResultWrapper<T>,
        onSuccess: suspend (T) -> Unit,
        onError: suspend (ResultWrapper.Error) -> Unit
    ): T? {
        return when (val response = call()) {
            is ResultWrapper.Success -> {
                onSuccess(response.value)
                response.value
            }
            is ResultWrapper.Error -> {
                onError(response)
                null
            }
        }
    }

    // Saves multiple tasks to local DB
    private suspend fun saveTaskLocally(task: Task) {
        Logger.w { "$logTag: Saving task locally: ${task.id}" }
        taskDao.upsertTask(task)
    }

    // Deletes task from local DB
    private suspend fun removeTaskLocally(task: Task) {
        Logger.w { "$logTag: Removing task locally: ${task.id}" }
        taskDao.deleteTask(task)
    }
}

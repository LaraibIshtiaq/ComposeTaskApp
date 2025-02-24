package home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import database.TaskDao
import kotlinx.coroutines.launch
import data.model.Priority
import data.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(private val taskRepository: TaskRepository,
    // Pass in the DAO as needed for saving/retrieving from the local database
    private val taskDao: TaskDao

): ViewModel() {
    val priorities: List<Priority> = Priority.entries

    // Holds the visibility state of the dialog
    private val _shouldShowDialog = mutableStateOf(false)
    val shouldShowDialog: MutableState<Boolean> get() = _shouldShowDialog

    private val _userId: MutableState<Int?> = mutableStateOf(null)
    val userId: MutableState<Int?> get() = _userId

    var tasks : MutableState<List<Task>> = mutableStateOf(listOf())

    fun setUserId(id: Int) {
        Logger.w("LogTASKs") { "setUserId called with ID: $id" }
        _userId.value = id
        loadTasks()
    }

    private fun updateTasks(newTasks: List<Task>) {
        tasks.value = tasks.value.toMutableList().apply {
            newTasks.forEach { newTask ->
                val existingIndex = indexOfFirst { it.id == newTask.id }
                if (existingIndex != -1) {
                    // Replace existing task
                    set(existingIndex, newTask)
                } else {
                    // Add new task
                    add(newTask)
                }
            }
        }
    }

    // Function to load tasks from the database
    private fun loadTasks() {
        viewModelScope.launch {
            _userId.value?.let { id ->
                taskRepository.getTasksByUserId(id).collect { taskList ->
                    Logger.w("LogTASKs") { "Loaded Tasks: $taskList" }
                    updateTasks(taskList)
                }
                hideAddTaskDialog()
            }
            Logger.w("LogTASKs") { "user id $_userId" }
        }
    }


    //Show dialog for adding tasks
    fun showAddTaskDialog() {
        _shouldShowDialog.value = true
    }

    //Hide dialog for adding tasks
    private fun hideAddTaskDialog() {
        _shouldShowDialog.value = false
    }

    // Adds or updates a task in the database
    fun upsertTask(task: Task) {
        Logger.w("LogTASKs") { "upsert task called" }
        viewModelScope.launch {

            taskRepository.createTask(task).collect { newTask ->
                Logger.w("LogTASKs") { "Fetched tasks create: $newTask" }
                updateTasks(listOf(newTask))
            }
            //hide the task dialog
            hideAddTaskDialog()
        }
    }

    //Updates a task in the database
    fun updateTask(task: Task) {
        Logger.w("LogTASKs") { "update task called" }
        viewModelScope.launch {
            taskRepository.updateTask(task).collect { newTask ->
                Logger.w("LogTASKs") { "Fetched tasks update: $newTask" }
                updateTasks(listOf(newTask))
            }
            //hide the task dialog
            hideAddTaskDialog()
        }
    }

    // Deletes a task
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task).collect { newTask ->
                Logger.w("LogTASKs") { "Task Deleted: $newTask" }
                taskDao.deleteTask(task)

                // Remove the deleted task from the list
                tasks.value = tasks.value.filterNot { it.id == task.id }
            }
        }
    }
}
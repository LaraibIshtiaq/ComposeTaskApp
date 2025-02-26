package home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import data.model.Priority
import data.model.Task

class HomeViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    // Centralized log tag
    private val logTag = "HomeViewModel"

    /** List of available priorities for tasks */
    val priorities: List<Priority> = Priority.entries

    /** Controls the visibility of the Add Task dialog */
    val isAddTaskDialogVisible = mutableStateOf(false)

    /** Holds the current user ID */
    private val _userId: MutableState<Int?> = mutableStateOf(null)
    val userId: MutableState<Int?> get() = _userId

    /** Stores the list of tasks for the user */
    val tasks: MutableState<List<Task>> = mutableStateOf(emptyList())

    /**
     * Sets the user ID and loads tasks for the specified user.
     */
    fun setUserId(id: Int) {
        Logger.w(logTag) { "Setting userId: $id" }
        _userId.value = id
        loadTasks()
    }

    /**
     * Replaces the existing task list with a new list.
     */
    private fun replaceTasks(newTasks: List<Task>) {
        tasks.value = newTasks
    }

    /**
     * Fetches tasks for the current user from the repository.
     */
    fun loadTasks() {
        viewModelScope.launch {
            _userId.value?.let { id ->
                try {
                    taskRepository.getTasksByUserId(id).collect { taskList ->
                        Logger.w(logTag) { "Loaded Tasks: $taskList" }
                        replaceTasks(taskList)
                    }
                } catch (e: Exception) {
                    Logger.e(logTag) { "Error loading tasks: $e" }
                }
            } ?: Logger.w(logTag) { "User ID is null, skipping task loading" }
        }
    }

    /**
     * Displays the Add Task dialog.
     */
    fun showAddTaskDialog() {
        isAddTaskDialogVisible.value = true
    }

    /**
     * Hides the Add Task dialog.
     */
    fun hideAddTaskDialog() {
        isAddTaskDialogVisible.value = false
    }

    /**
     * Adds a new task to the database and updates the task list.
     */
    fun addTask(task: Task) {
        Logger.w(logTag) { "Adding task: $task" }
        viewModelScope.launch {
            try {
                taskRepository.createTask(task).collect { newTask ->
                    Logger.w(logTag) { "Task created: $newTask" }
                    tasks.value += newTask
                }
                hideAddTaskDialog()
            } catch (e: Exception) {
                Logger.e(logTag) { "Error adding task: $e" }
            }
        }
    }

    /**
     * Updates an existing task in the database.
     */
    fun updateTask(task: Task) {
        Logger.w(logTag) { "Updating task: $task" }
        viewModelScope.launch {
            try {
                taskRepository.updateTask(task).collect { updatedTask ->
                    Logger.w(logTag) { "Task updated: $updatedTask" }
                    tasks.value = tasks.value.map { if (it.id == updatedTask.id) updatedTask else it }
                }
                hideAddTaskDialog()
            } catch (e: Exception) {
                Logger.e(logTag) { "Error updating task: $e" }
            }
        }
    }

    /**
     * Deletes a task from the database and removes it from the task list.
     */
    fun deleteTask(task: Task) {
        Logger.w(logTag) { "Deleting task: ${task.id}" }
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(task).collect {
                    tasks.value = tasks.value.filterNot { it.id == task.id }
                    Logger.w(logTag) { "Task deleted: ${task.id}" }
                }
            } catch (e: Exception) {
                Logger.e(logTag) { "Error deleting task: $e" }
            }
        }
    }
}

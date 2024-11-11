package home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import database.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import data.model.Priority
import data.model.Task

class HomeViewModel(private val taskDao: TaskDao): ViewModel() {

    val priorities: List<Priority> = Priority.entries

    // Holds the visibility state of the dialog
    private val _shouldShowDialog = mutableStateOf(false)
    val shouldShowDialog: MutableState<Boolean> get() = _shouldShowDialog

    // Holds the tasks state from the database
    val tasks: Flow<List<Task>> = taskDao.getAllTasks()

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
        viewModelScope.launch {
            taskDao.upsertTask(task)
            hideAddTaskDialog()
        }
    }

    // Deletes a task
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }
}
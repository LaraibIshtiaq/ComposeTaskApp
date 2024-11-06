package com.example

import com.example.model.Priority
import com.example.model.Task
import com.example.model.TaskRepository

//This is a fake testing repository which is used to test only.
class FakeTestRepository : TaskRepository {
    private val tasks = mutableListOf(
        Task("cleaning", "Clean the house", Priority.Low),
        Task("gardening", "Mow the lawn", Priority.Medium),
        Task("shopping", "Buy the groceries", Priority.High),
        Task("painting", "Paint the fence", Priority.Medium)
    )

    ///Returning all tasks
    override suspend fun allTasks(): List<Task> {
        return tasks
    }

    //Filtering tasks by a priority
    override suspend fun tasksByPriority(priority: Priority): List<Task> {
        return tasks.filter {
            it.priority == priority
        }
    }

    //Returning a task by its name
    override suspend fun taskByName(name: String) = tasks.find {
        it.name.equals(name, ignoreCase = true)
    }

    //Adding a task to the list
    override suspend fun addTask(task: Task) {
        if (taskByName(task.name) != null) {
            throw IllegalStateException("Cannot duplicate task names!")
        }
        tasks.add(task)
    }

    //Removing a task from the list
    override suspend fun removeTask(name: String): Boolean {
        return tasks.removeIf { it.name == name }
    }

}
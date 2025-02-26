package com.example.repository

import com.example.model.Priority
import com.example.model.Task


interface TaskRepository {
    //suspend keyword will allow implementations of the interface methods to start jobs of work on
    // a different Coroutine Dispatcher.
    suspend fun allTasks(): List<Task>
    suspend fun addTask(task: Task): Task
    suspend fun removeTask(id: Int): Boolean
    suspend fun updateTask(task:Task) :Task
    suspend fun tasksByUserId(userId: String): List<Task>
}
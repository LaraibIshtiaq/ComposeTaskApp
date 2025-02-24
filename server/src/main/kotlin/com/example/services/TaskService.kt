package com.example.services

import com.example.model.Task
import com.example.repository.TaskRepository

class TaskService(private val taskRepository: TaskRepository) {

    suspend fun createTask(task: Task) :Task {
        println("taskService createTask called with taskRequest: $task")
        taskRepository.addTask(task)
        return task
    }

    suspend fun getAllTasks(userId: Int): List<Task> {
        println("TaskService getAllTasks called with no parameters $userId")
        val tasks: List<Task> = taskRepository.tasksByUserId(userId.toString())
        println("tasks found in TaskService");
        println("IN TASK SERVICE $tasks")
        return tasks
    }

    suspend fun updateTask(task: Task): Task {
        println("TaskService updateTask called with task: $task")
        val updatedTask = taskRepository.updateTask(task)
        return updatedTask
    }

    suspend fun deleteTask(taskId: Int): Boolean {
        println("TaskService deleteTask called with taskId: $taskId")
        return taskRepository.removeTask(taskId)
    }
}
package com.example.services

import com.example.model.Task
import com.example.repository.TaskRepository
import java.util.logging.Logger

class TaskService(private val taskRepository: TaskRepository) {

    private val logger: Logger = Logger.getLogger(TaskService::class.java.name)

    suspend fun createTask(task: Task): Task {
        logger.info("Creating new task: $task")
        taskRepository.addTask(task)
        return task.also { logger.info("Task created successfully with ID: ${it.id}") }
    }

    suspend fun getAllTasks(userId: Int): List<Task> {
        logger.info("Fetching all tasks for user ID: $userId")
        return taskRepository.tasksByUserId(userId.toString()).also {
            logger.info("Found ${it.size} tasks for user ID: $userId")
        }
    }

    suspend fun updateTask(task: Task): Task {
        logger.info("Updating task with ID: ${task.id}")
        return taskRepository.updateTask(task).also {
            logger.info("Task updated successfully: $it")
        }
    }

    suspend fun deleteTask(taskId: Int): Boolean {
        logger.info("Deleting task with ID: $taskId")
        return taskRepository.removeTask(taskId).also { success ->
            if (success) {
                logger.info("Task deleted successfully with ID: $taskId")
            } else {
                logger.warning("Failed to delete task with ID: $taskId")
            }
        }
    }
}
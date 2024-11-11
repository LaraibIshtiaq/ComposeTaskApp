package com.example.repository

import com.example.db.TaskDAO
import com.example.db.TaskTable
import com.example.db.daoToModel
import com.example.db.suspendTransaction
import com.example.model.Priority
import com.example.model.Task

// Repository for managing task data using a PostgreSQL database, implementing TaskRepository interface.
class PostgresTaskRepository: TaskRepository {

    // Fetches all tasks from the database as a list of Task objects.
    override suspend fun allTasks(): List<Task> {
        return suspendTransaction {
            // Retrieves all TaskDAO records and maps them to Task model objects.
            TaskDAO.all().map(::daoToModel)
        }
    }

    // Fetches tasks filtered by the specified priority level.
    override suspend fun tasksByPriority(priority: Priority): List<Task> {
        return suspendTransaction {
            // Finds tasks in TaskDAO where the priority matches the given priority and maps them to Task model objects.
            TaskDAO.find { TaskTable.priority eq priority.name }.map(::daoToModel)
        }
    }

    // Retrieves a task by its name, returning null if no task is found.
    override suspend fun taskByName(name: String): Task? {
        return suspendTransaction {
            // Finds the first TaskDAO entry with the given name, converts it to a Task model, or returns null if not found.
            TaskDAO
                .find { TaskTable.name eq name }
                .limit(1)
                .map(::daoToModel)
                .firstOrNull()
        }
    }

    // Adds a new task to the database.
    override suspend fun addTask(task: Task) {
        return suspendTransaction {
            // Creates a new TaskDAO entry with the specified task details.
            TaskDAO.new {
                name = task.name
                description = task.description
                priority = task.priority.name
            }
        }
    }

    // Removes a task by its name, returning true if successful, false if the task is not found.
    override suspend fun removeTask(name: String): Boolean {
        return suspendTransaction {
            // Finds the first TaskDAO entry with the given name and deletes it; returns true if deleted, false otherwise.
            TaskDAO.find { TaskTable.name eq name }.firstOrNull()?.delete() != null
        }
    }
}

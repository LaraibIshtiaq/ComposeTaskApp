package com.example.model

import com.example.db.TaskDAO
import com.example.db.TaskTable
import com.example.db.daoToModel
import com.example.db.suspendTransaction

class PostgresTaskRepository: TaskRepository {
    override suspend fun allTasks(): List<Task> {
        return suspendTransaction {
            TaskDAO.all().map(::daoToModel)
        }
    }

    override suspend fun tasksByPriority(priority: Priority): List<Task> {
        return suspendTransaction {
            TaskDAO.find { TaskTable.priority eq priority.name }.map(::daoToModel)
        }
    }

    override suspend fun taskByName(name: String): Task? {
        return suspendTransaction {
            TaskDAO
                .find { TaskTable.name eq name }
                .limit(1)
                .map(::daoToModel)
                .firstOrNull()
        }
    }

    override suspend fun addTask(task: Task) {
        return suspendTransaction {
            TaskDAO.new {
                name = task.name
                description = task.description
                priority = task.priority.name
            }
        }
    }

    override suspend fun removeTask(name: String): Boolean {
        return suspendTransaction {
            TaskDAO.find { TaskTable.name eq name }.firstOrNull()?.delete() != null
        }
    }

}
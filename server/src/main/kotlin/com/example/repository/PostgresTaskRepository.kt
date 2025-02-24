package com.example.repository

import com.example.db.tables.TaskTable
import com.example.model.Priority
import com.example.model.Task
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.update

// Repository for managing task data using a PostgreSQL database, implementing TaskRepository interface.
class PostgresTaskRepository : TaskRepository {

    override suspend fun allTasks(): List<Task> = newSuspendedTransaction {
        TaskTable.selectAll().map { it.toTask() }
    }

    override suspend fun addTask(task: Task) = newSuspendedTransaction {
        println("UserRepository createUser called with user: $task")
        val id = TaskTable.insert {
            it[id] = task.id
            it[title] = task.title
            it[description] = task.description
            it[priority] = task.priority.name
            it[userId] = task.userId
        } get TaskTable.id
        Task(id, task.title, task.description, task.priority, task.userId)
    }


    override suspend fun removeTask(id: Int): Boolean = newSuspendedTransaction {
        val rowsDeleted = TaskTable.deleteWhere {
            TaskTable.id eq id
        }
        rowsDeleted == 1
    }

    override suspend fun updateTask(task: Task): Task = newSuspendedTransaction {
        TaskTable.update({ TaskTable.id eq task.id }) {
            it[title] = task.title
            it[description] = task.description
            it[priority] = task.priority.name
            it[userId] = task.userId
        }
        task // Return the updated task
    }


    override suspend fun tasksByUserId(userId: String): List<Task> = newSuspendedTransaction {
        val userIdInt = userId.toInt()
        TaskTable.select {
            TaskTable.userId eq userIdInt
        }
            .map { it.toTask() }
    }

    private fun ResultRow.toTask(): Task = Task(
        id = this[TaskTable.id],
        title = this[TaskTable.title],
        description = this[TaskTable.description],
        priority = Priority.valueOf(this[TaskTable.priority]),
        userId = this[TaskTable.userId]
    )
}
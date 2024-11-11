package com.example.db

import com.example.model.Priority
import com.example.model.Task
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction


// Defines the TaskTable schema for tasks in the database, inheriting from IntIdTable with "task"
// as the table name.
object TaskTable : IntIdTable("task") {
    val name = varchar("name", 50)
    val description = varchar("description", 50)
    val priority = varchar("priority", 50)
}


// Data Access Object (DAO) class for the Task entity, representing a row in TaskTable.
class TaskDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TaskDAO>(TaskTable)

    var name by TaskTable.name
    var description by TaskTable.description
    var priority by TaskTable.priority
}

//This function takes a block of code and runs it within a database transaction,
// through the IO Dispatcher. This is designed to offload blocking jobs of work onto a thread pool.
suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

// Converts a TaskDAO instance to a Task model object.
// Maps the DAO properties to the Task data class, converting the priority to a Priority enum.
fun daoToModel(dao: TaskDAO) = Task(
    dao.name,
    dao.description,
    Priority.valueOf(dao.priority)
)
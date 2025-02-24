package com.example.db.tables

import org.jetbrains.exposed.sql.Table

// Defines the UserTable schema for users in the database,
// inheriting from Table with "users" as the table name.
object TaskTable : Table("task") {
    val id = integer("id")
    val title = varchar("title", 255).uniqueIndex()
    val description = varchar("description", 255)
    val priority = varchar("priority", 255)
    val userId = integer("userid")
    override val primaryKey = PrimaryKey(id)
}
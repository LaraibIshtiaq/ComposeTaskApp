package com.example.db.tables

import org.jetbrains.exposed.sql.Table

// Defines the UserTable schema for users in the database,
// inheriting from Table with "users" as the table name.
object UserTable : Table("users") {
    val id = long("id").autoIncrement()
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val name = varchar("name", 255)
    override val primaryKey = PrimaryKey(id)
}
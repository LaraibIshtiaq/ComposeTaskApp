package com.example.repository

import com.example.db.tables.UserTable
import com.example.model.User
import com.example.model.UserRequest
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    fun findUserByEmail(email: String): User? = transaction {
        println("UserRepository findUserByEmail called with user: $email")

        //select * from users where email = email
        UserTable.selectAll().where { UserTable.email eq email }.map {
            User(
                it[UserTable.name],
                it[UserTable.email],
                it[UserTable.id]
            )
        }.singleOrNull()
    }

    fun createUser(user: UserRequest): User = transaction {
        println("UserRepository createUser called with user: $user")
        val id = UserTable.insert {
            it[name] = user.name
            it[email] = user.email
            it[password] = user.password
        } get UserTable.id
        User(user.name, user.email, id)
    }

    fun signIn(email: String, password: String): User = transaction {
        UserTable.select { UserTable.email eq email and (UserTable.password eq password) }.map {
            User(
                it[UserTable.name],
                it[UserTable.email],
                it[UserTable.id]
            )
        }.first()
    }
}
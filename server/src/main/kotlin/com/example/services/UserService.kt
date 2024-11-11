package com.example.services

import com.auth0.jwt.JWT
import com.example.model.User
import com.example.model.UserRequest
import com.example.repository.UserRepository
import java.security.MessageDigest

class UserService(private val userRepository: UserRepository) {

    suspend fun createUser(userRequest: UserRequest): User {
        val existingUser = userRepository.findUserByEmail(userRequest.email)
        if (existingUser != null) {
            throw IllegalArgumentException("User with email ${userRequest.email} already exists")
        }
        val user =
            userRepository.createUser(userRequest.copy(password = hashPassword(userRequest.password)))
        return user
    }

    suspend fun loginUser(email: String, password: String): User {
        val hashedPassword = hashPassword(password)
        val user = userRepository.signIn(email, hashedPassword)
            ?: throw IllegalArgumentException("Invalid credentials")
        return user
    }

    fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(password.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
    }
}
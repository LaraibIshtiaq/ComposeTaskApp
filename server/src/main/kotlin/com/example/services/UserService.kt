package com.example.services

import com.example.model.User
import com.example.model.UserRequest
import com.example.repository.UserRepository
import java.security.MessageDigest
import java.util.logging.Logger

class UserService(private val userRepository: UserRepository) {

    private val logger: Logger = Logger.getLogger(UserService::class.java.name)

    fun createUser(userRequest: UserRequest): User {
        logger.info("Creating user with email: ${userRequest.email}")

        require(userRequest.email.isNotBlank()) { "Email cannot be blank" }
        require(userRequest.password.isNotBlank()) { "Password cannot be blank" }

        userRepository.findUserByEmail(userRequest.email)?.let {
            throw IllegalArgumentException("User with email ${userRequest.email} already exists")
        }

        return userRepository.createUser(
            userRequest.copy(password = hashPassword(userRequest.password))
        ).also {
            logger.info("User created successfully with ID: ${it.id}")
        }
    }

    fun loginUser(email: String, password: String): User {
        logger.info("Attempting login for email: $email")

        val hashedPassword = hashPassword(password)
        return userRepository.signIn(email, hashedPassword)
    }

    companion object {
        fun hashPassword(password: String): String {
            return MessageDigest.getInstance("SHA-256")
                .digest(password.toByteArray())
                .joinToString("") { "%02x".format(it) }
        }
    }
}

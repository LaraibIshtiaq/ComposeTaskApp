package com.example.services

import com.example.model.User
import com.example.model.UserRequest
import com.example.repository.UserRepository
import java.security.MessageDigest

class UserService(private val userRepository: UserRepository) {

    fun createUser(userRequest: UserRequest): User {
        println("UserService createUser called with userRequest: $userRequest")
        val existingUser = userRepository.findUserByEmail(userRequest.email)
        if (existingUser != null) {
            throw IllegalArgumentException("User with email ${userRequest.email} already exists")
        }
        val user =
            userRepository.createUser(userRequest.copy(password = hashPassword(userRequest.password)))
        return user
    }

    fun loginUser(email: String, password: String): User {
        println("UserService loginUser called with userRequest: $email $password")
        val hashedPassword = hashPassword(password)
        val user: User = userRepository.signIn(email, hashedPassword)
            ?: throw IllegalArgumentException("Invalid credentials")
        println("***********USER LOGGED IN************")
        println(user.id)
        println(user.name)
        println(user.email) 
        return user
    }

    fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(password.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
    }
}
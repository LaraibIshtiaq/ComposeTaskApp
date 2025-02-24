package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id : Int,
    val title: String,
    val description: String,
    val priority: Priority,
    val userId: Int // Foreign key referring to User table
)

//enum to represent priorities
enum class Priority {
    Low, Medium, High, Vital
}
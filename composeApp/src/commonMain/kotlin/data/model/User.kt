package data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: Long,
    val name: String,
    val email: String,
)
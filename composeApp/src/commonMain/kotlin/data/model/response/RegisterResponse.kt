package data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(val name: String, val email: String, val id: Int)
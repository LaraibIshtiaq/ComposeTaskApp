package data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(val email: String, val password: String, val name: String)
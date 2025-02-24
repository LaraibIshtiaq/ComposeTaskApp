package data.model.response

import data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(val name: String, val email: String, val id: Int) {
    fun toUserEntity(): User {
        return User(
            id,
            name,
            email
        )
    }
}
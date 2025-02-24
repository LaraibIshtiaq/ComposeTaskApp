package com.example.model

import kotlinx.serialization.Serializable

@Serializable
///Used for sending request of signup to server
data class UserRequest(var id : Int, val email: String, val password: String, val name: String)
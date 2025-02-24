package com.example.model

import kotlinx.serialization.Serializable

@Serializable
//The table returned from repo to Viewmodel in response of correct authentication
data class User(val name: String, val email: String, val id: Long)
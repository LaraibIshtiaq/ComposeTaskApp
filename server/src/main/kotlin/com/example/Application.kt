package com.example

import com.example.repository.PostgresTaskRepository
import com.example.plugins.configureDatabases
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.server.application.Application

// Main function entry point for the Ktor application.
fun main(args: Array<String>) {
    // Starts the Ktor server using Netty as the engine.
    io.ktor.server.netty.EngineMain.main(args)
}

// Ktor Application module, serving as the main configuration setup for the server.
fun Application.module() {
    // Creates an instance of PostgresTaskRepository to handle task-related database operations.
    val postgresRepository = PostgresTaskRepository()

    // Configures serialization for handling JSON or other formats using the repository.
    configureSerialization(postgresRepository)

    // Sets up database configurations (e.g., initializing the connection).
    configureDatabases()

    // Configures the routing for handling HTTP requests.
    configureRouting()
}
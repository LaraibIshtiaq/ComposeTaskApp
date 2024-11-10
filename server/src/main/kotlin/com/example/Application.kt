package com.example

import com.example.model.PostgresTaskRepository
import com.example.plugins.configureDatabases
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val postgresRepository = PostgresTaskRepository()
//    configureSecurity()
    configureSerialization(postgresRepository)
    configureDatabases()
//    configureHTTP()
    configureRouting()
}

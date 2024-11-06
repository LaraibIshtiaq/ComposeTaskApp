package com.example

import com.example.model.PostgresTaskRepository
import com.example.plugins.*
import io.ktor.server.application.*

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

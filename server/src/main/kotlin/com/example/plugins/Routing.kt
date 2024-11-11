package com.example.plugins

import com.example.repository.UserRepository
import com.example.routes.userRoutes
import com.example.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

// Configures routing for handling different HTTP requests and sets up exception handling.
fun Application.configureRouting() {

    // Installs StatusPages feature to handle exceptions and respond with custom error messages.
    install(StatusPages) {
        // Catches all exceptions and responds with a 500 Internal Server Error status, displaying the error cause.
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }

    // Defines routing for handling different HTTP requests.
    routing {
        // Handles a GET request to the root URL ("/") and responds with a "Hello World!" message.
        get("/") {
            call.respondText("Hello World!")
        }

        // Serves static resources from the "static" directory, accessible under the "/static" path.
        // For example, accessing "/static/index.html" will serve "static/index.html".
        staticResources("/static", "static")

        // Calls the userRoutes function to set up user-related routes, passing an instance of UserService
        // that depends on UserRepository for handling user data operations.
        userRoutes(UserService(UserRepository()))
    }
}


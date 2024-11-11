package com.example.plugins

import com.example.model.Priority
import com.example.model.Task
import com.example.repository.TaskRepository
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureSerialization(repository: TaskRepository) {
    install(ContentNegotiation) {
        json()
    }
    routing {
        route("/tasks") {
            // Handles GET requests to retrieve all tasks.
            get {
                // Fetches all tasks from the repository and responds with the list.
                val tasks = repository.allTasks()
                call.respond(tasks)
            }

            // Handles GET requests to retrieve a task by its name.
            get("/byName/{taskName}") {
                // Gets the task name from the URL parameter.
                val name = call.parameters["taskName"]
                if (name == null) {
                    // Responds with 400 Bad Request if the name parameter is missing.
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                // Retrieves the task by name and responds with it, or 404 Not Found if not found.
                val task = repository.taskByName(name)
                if (task == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }
                call.respond(task)
            }

            // Handles GET requests to retrieve tasks filtered by priority.
            get("/byPriority/{priority}") {
                // Gets the priority level from the URL parameter.
                val priorityAsText = call.parameters["priority"]
                if (priorityAsText == null) {
                    // Responds with 400 Bad Request if the priority parameter is missing.
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                try {
                    // Attempts to convert the parameter to a Priority enum.
                    val priority = Priority.valueOf(priorityAsText)
                    val tasks = repository.tasksByPriority(priority)

                    // Responds with 404 Not Found if no tasks match the priority.
                    if (tasks.isEmpty()) {
                        call.respond(HttpStatusCode.NotFound)
                        return@get
                    }
                    call.respond(tasks)
                } catch (ex: IllegalArgumentException) {
                    // Responds with 400 Bad Request if the priority is invalid.
                    call.respond(HttpStatusCode.BadRequest)
                }
            }
            // Handles POST requests to add a new task.
            post {
                try {
                    // Receives the Task object from the request body and adds it to the repository.
                    val task = call.receive<Task>()
                    repository.addTask(task)
                    call.respond(HttpStatusCode.NoContent) // Responds with 204 No Content on success.
                } catch (ex: IllegalStateException) {
                    // Responds with 400 Bad Request if the task data is invalid.
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    // Handles JSON conversion errors and responds with 400 Bad Request.
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            // Handles DELETE requests to remove a task by its name.
            delete("/{taskName}") {
                // Gets the task name from the URL parameter.
                val name = call.parameters["taskName"]
                if (name == null) {
                    // Responds with 400 Bad Request if the name parameter is missing.
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }
                // Attempts to delete the task and responds with 204 No Content if successful, 404 if not found.
                if (repository.removeTask(name)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}
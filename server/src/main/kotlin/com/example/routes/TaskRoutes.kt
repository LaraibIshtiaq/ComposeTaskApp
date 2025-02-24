package com.example.routes

import com.example.model.Task
import com.example.services.TaskService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.taskRoutes(taskService: TaskService) {
    route("/task") {
        post("/create") {
            val request = call.receive<Task>()
            val response = taskService.createTask(request)
            println("RESPONSE IN TASKROUTE CREATE $response")
            call.respond(HttpStatusCode.OK, response)
        }
        get("/all") {
            // Get userId from query parameter
            val userId = call.request.queryParameters["userId"]?.toInt()
            println("userId in task routes $userId");
            if(userId != null) {
                val response = taskService.getAllTasks(userId)
                println("RESPONSE IN TASKROUTE ALL $response")
                call.respond(HttpStatusCode.OK, response)
            }
        }
        put("/update") {
            val request = call.receive<Task>()
            val response = taskService.updateTask(request)
            println("RESPONSE IN TASKROUTE UPDATE $response")
            call.respond(HttpStatusCode.OK, response)
        }

        delete("/delete") {
            val taskId = call.parameters["id"]?.toInt()
            if (taskId != null) {
                val response = taskService.deleteTask(taskId)
                println("RESPONSE IN TASKROUTE DELETE $response")
                call.respond(HttpStatusCode.OK, response)
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid Task ID")
            }
        }
    }
}
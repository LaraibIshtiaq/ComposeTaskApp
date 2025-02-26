package data

import co.touchlab.kermit.Logger
import data.model.Task
import data.model.request.LoginRequest
import data.model.request.SignupRequest
import data.model.response.RegisterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.errors.IOException

expect val baseURL: String

class NetworkService(private val httpClient: HttpClient) {

    private val logger = Logger.withTag("NetworkService")

    suspend fun register(request: SignupRequest): ResultWrapper<RegisterResponse> =
        makeWebRequest("$baseURL/users/register", HttpMethod.Post, body = request)

    suspend fun login(request: LoginRequest): ResultWrapper<RegisterResponse> =
        makeWebRequest("$baseURL/users/login", HttpMethod.Post, body = request)

    suspend fun addTask(task: Task): ResultWrapper<Task> {
        return makeWebRequest("$baseURL/task/create", HttpMethod.Post, body = task) }

    suspend fun updateTask(task: Task): ResultWrapper<Task> =
        makeWebRequest("$baseURL/task/update", HttpMethod.Put, body = task)

    suspend fun deleteTask(taskId: Int): ResultWrapper<Boolean> =
        makeWebRequest("$baseURL/task/delete", HttpMethod.Delete, parameters = mapOf("id" to taskId.toString()))

    suspend fun getTasksForUser(userId: Int): ResultWrapper<List<Task>> =
        makeWebRequest("$baseURL/task/all", HttpMethod.Get, parameters = mapOf("userId" to userId.toString()))

    private suspend inline fun <reified T> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): ResultWrapper<T> {
        return try {
            logger.i { "Requesting $method $url" }

            val response = httpClient.request(url) {
                this.method = method
                contentType(ContentType.Application.Json)
                headers.forEach { (key, value) -> this.headers.append(key, value) }
                parameters.forEach { (key, value) -> parameter(key, value) }
                body?.let { setBody(it) }
            }

            val responseBody = response.body<T>()
            logger.i { "Response received: ${response.status} | Parsed: $responseBody" }

            ResultWrapper.Success(responseBody)
        } catch (e: ClientRequestException) {
            logger.e(e) { "Client error: ${e.response.status}" }
            ResultWrapper.Error(Exception("Client error: ${e.response.status}"))
        } catch (e: ServerResponseException) {
            logger.e(e) { "Server error: ${e.response.status}" }
            ResultWrapper.Error(Exception("Server error: ${e.response.status}"))
        } catch (e: IOException) {
            logger.e(e) { "Network error: ${e.message}" }
            ResultWrapper.Error(Exception("Network connectivity issue: ${e.message}"))
        } catch (e: Exception) {
            logger.e(e) { "Unexpected error: ${e.message}" }
            ResultWrapper.Error(Exception("Unexpected error: ${e.message}"))
        }
    }
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val e: Exception) : ResultWrapper<Nothing>()
}

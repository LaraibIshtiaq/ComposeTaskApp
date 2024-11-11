package data

import data.model.request.LoginRequest
import data.model.request.SignupRequest
import data.model.response.RegisterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
expect val baseURL:String


// The NetworkService class provides methods for making HTTP requests to the backend.
class NetworkService(val httpClient: HttpClient) {

    // Registers a user by sending a POST request with the signup request body to the backend.
    suspend fun register(request: SignupRequest): ResultWrapper<RegisterResponse> {
        // Calls the makeWebRequest method to send the request and returns the result
        return makeWebRequest<RegisterResponse>(
            "$baseURL/users/register",
            HttpMethod.Post,
            body = request
        )
    }

    // Logs in a user by sending a POST request with the login request body to the backend.
    suspend fun login(request: LoginRequest): ResultWrapper<RegisterResponse> {
        // Calls the makeWebRequest method to send the request and returns the result
        return makeWebRequest<RegisterResponse>(
            "$baseURL/users/login",
            HttpMethod.Post,
            body = request
        )
    }

    // A generic function to make HTTP requests to any URL with the specified HTTP method and body.
    // It can handle any response type T.
    suspend inline fun <reified T> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): ResultWrapper<T> {
        return try {

            // Send the HTTP request using the provided parameters and body
            val response = httpClient.request(url) {
                this.method = method
                headers.forEach { (key, value) ->
                    this.headers.append(key, value)
                }
                parameters.forEach { (key, value) ->
                    this.parameter(key, value)
                }
                if (body != null) {
                    this.setBody(body)
                }
                // Specify content type as JSON
                contentType(ContentType.Application.Json)
            }.body<T>()// Deserialize the response body into the specified type T
            ResultWrapper.Success(response)
        } catch (e: ClientRequestException) {
            // Catch any client-side errors (e.g., network issues, malformed requests)
            ResultWrapper.Error(e)  // Return an error result with the exception
        } catch (e: ServerResponseException) {
            // Catch any server-side errors (e.g., 500 or 404 errors)
            ResultWrapper.Error(e)  // Return an error result with the exception
        } catch (e: IOException) {
            // Catch any I/O errors (e.g., network connectivity issues)
            ResultWrapper.Error(e)  // Return an error result with the exception
        } catch (e: Exception) {
            // Catch any other unexpected errors
            ResultWrapper.Error(e)  // Return an error result with the exception
        }
    }
}

// Sealed class used to represent the result of an HTTP request, either successful or failed.
sealed class ResultWrapper<out T> {
    // Success case, wraps the result of type T.
    data class Success<out T>(val value: T) : ResultWrapper<T>()

    // Error case, wraps an exception that occurred during the request.
    data class Error(val e: Exception) : ResultWrapper<Nothing>()
}
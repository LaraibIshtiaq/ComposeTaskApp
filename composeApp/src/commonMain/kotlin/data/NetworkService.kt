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

class NetworkService(val httpClient: HttpClient) {

    suspend fun register(request: SignupRequest): ResultWrapper<RegisterResponse> {
        return makeWebRequest<RegisterResponse>(
            "$baseURL/users/register",
            HttpMethod.Post,
            body = request
        )
    }

    suspend fun login(request: LoginRequest): ResultWrapper<RegisterResponse> {
        return makeWebRequest<RegisterResponse>(
            "$baseURL/users/login",
            HttpMethod.Post,
            body = request
        )
    }

    suspend inline fun <reified T> makeWebRequest(
        url: String,
        method: HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        parameters: Map<String, String> = emptyMap()
    ): ResultWrapper<T> {
        return try {
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
                contentType(ContentType.Application.Json)
            }.body<T>()
            ResultWrapper.Success(response)
        } catch (e: ClientRequestException) {
            ResultWrapper.Error(e)
        } catch (e: ServerResponseException) {
            ResultWrapper.Error(e)
        } catch (e: IOException) {
            ResultWrapper.Error(e)
        } catch (e: Exception) {
            ResultWrapper.Error(e)
        }
    }
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val e: Exception) : ResultWrapper<Nothing>()
}
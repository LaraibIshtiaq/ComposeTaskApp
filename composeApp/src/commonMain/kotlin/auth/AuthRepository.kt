package auth

import data.NetworkService
import data.ResultWrapper
import data.model.request.SignupRequest
import data.model.response.RegisterResponse
import database.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(
    private val networkService: NetworkService,
    // Pass in the DAO as needed for saving/retrieving from the database
    private val userDao: UserDao
) {

    // Function to register a user. Checks network response and saves to local storage if successful.
    suspend fun registerUser(name: String, email: String, password: String): Flow<ResultWrapper<RegisterResponse>> = flow {
        // Send the network request to register the user
        val response = networkService.register(SignupRequest(name = name, email = email, password = password))
        // Emit the response to ViewModel, handling success or error
        when (response) {
            is ResultWrapper.Success -> {
                println(response.value)
                saveUserLocally(response.value)
                emit(response)
            }
            is ResultWrapper.Error -> {
                emit(response)
            }
        }
    }

    // Saves user data locally in the Room database
    private suspend fun saveUserLocally(user: RegisterResponse) {
        userDao.addUser(user.toUserEntity())
    }
}

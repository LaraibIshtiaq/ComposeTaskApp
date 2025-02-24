package auth

import auth.login.LoginState
import data.NetworkService
import data.ResultWrapper
import data.model.request.LoginRequest
import data.model.request.SignupRequest
import data.model.response.RegisterResponse
import database.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(
    private val networkService: NetworkService,
    // Pass in the DAO as needed for saving/retrieving from the local database
    private val userDao: UserDao
) {

    // Function to register a user. Checks network response and saves to local storage if successful.
    suspend fun registerUser(name: String, email: String, password: String): Flow<ResultWrapper<RegisterResponse>> = flow {
        // Send the network request to register the user
        val response = networkService.register(SignupRequest(id= 0, name = name, email = email, password = password))
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

    suspend fun loginUser(email: String, password: String): Flow<ResultWrapper<RegisterResponse>> = flow {
        // Send the network request to login the user
        // Emit the response to ViewModel, handling success or error
        // Handles the network result based on success or error.
        when (val response = networkService.login(LoginRequest(email, password))) {
            // On success, sets the UI state to Success and saves the user data.
            is ResultWrapper.Success -> {
                saveUserLocally(response.value)
                emit(response)
            }
            // On error, sets the UI state to Error with the error message.
            is ResultWrapper.Error -> {
                emit(response)
            }
        }
    }

    // Saves user data locally in the Room database
    private suspend fun saveUserLocally(user: RegisterResponse) {
        println("AuthRepository saveUserLocally called with user: $user")
        userDao.addUser(user.toUserEntity())
    }
}

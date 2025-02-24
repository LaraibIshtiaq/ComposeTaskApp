package auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import auth.AuthRepository
import auth.signup.SignupState
import data.NetworkService
import data.ResultWrapper
import data.model.request.LoginRequest
import home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel class for handling user login logic and managing UI state.
class LoginViewModel(
    private val authRepository: AuthRepository): ViewModel() {
    // Backing property for the login state, initialized with Nothing as the default state.
    private val _uiState = MutableStateFlow<LoginState>(LoginState.Nothing)
    // Publicly exposed read-only view of the login state.
    val uiState = _uiState.asStateFlow()

    // Initiates the login process by calling the network service and updating the UI state.
    fun login(email: String, password: String, homeViewModel: HomeViewModel) {
        // Sets the UI state to Loading to indicate login is in progress.
        _uiState.value = LoginState.Loading

        // Launches a coroutine in the ViewModel's scope to handle asynchronous login.
        viewModelScope.launch {
            // Makes a network request to log in the user with the provided email and password.
            // Handles the network result based on success or error.
            authRepository.loginUser(email, password).collect { response ->
                when(response){
                    // On success, sets the UI state to Success and saves the user data.
                    is ResultWrapper.Success -> {
                        homeViewModel.setUserId(response.value.id)
                        _uiState.value = LoginState.Success
                    }
                    // On error, sets the UI state to Error with the error message.
                    is ResultWrapper.Error -> {
                        _uiState.value = LoginState.Error(response.e.message ?: "An error occurred")
                    }
                }
            }
        }
    }

    // Saves the user's data locally after a successful login.
    private fun saveUser(userId: Int) {
        // Implementation for saving the user data locally will go here.

    }

    // Resets the UI state to Nothing, allowing for retry attempts if needed.
    fun retry() {
        _uiState.value = LoginState.Nothing
    }
}

// Represents different states for the login process.
sealed class LoginState {
    // Indicates the initial or reset state where no action is being taken.
    object Nothing : LoginState()
    // Represents the loading state while login is in progress.
    object Loading : LoginState()
    // Represents a successful login.
    object Success : LoginState()
    // Represents an error during login, containing an error message.
    data class Error(val message: String) : LoginState()
}

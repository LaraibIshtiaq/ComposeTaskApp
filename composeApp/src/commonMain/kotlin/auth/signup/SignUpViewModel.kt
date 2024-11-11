package auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.NetworkService
import data.ResultWrapper
import data.model.request.SignupRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel class for handling user sign-up logic and managing UI state.
class SignUpViewModel(val networkService: NetworkService): ViewModel() {

    // Backing property for the sign-up state, initialized with Nothing as the default state.
    private val _uiState = MutableStateFlow<SignupState>(SignupState.Nothing)
    // Publicly exposed read-only view of the sign-up state.
    val uiState = _uiState.asStateFlow()

    // Registers a new user by calling the network service and updates the UI state accordingly.
    fun register(name: String, email: String, password: String) {
        // Sets the UI state to Loading to indicate sign-up is in progress.
        _uiState.value = SignupState.Loading
        // Launches a coroutine in the ViewModel's scope to handle asynchronous registration.
        viewModelScope.launch {
            // Makes a network request to register the user, passing the necessary sign-up details.
            val response = networkService.register(
                SignupRequest(
                    email = email,
                    password = password,
                    name = name
                )
            )
            // Handles the network response based on success or error.
            when (response) {
                // On success, sets the UI state to Success and saves the user locally.
                is ResultWrapper.Success -> {
                    _uiState.value = SignupState.Success
                    saveUserLocally()
                }
                // On error, sets the UI state to Error with the error message.
                is ResultWrapper.Error -> {
                    _uiState.value = SignupState.Error(response.e.message ?: "An error occurred")
                }
            }
        }
    }

    // Saves the user's data locally in the Room database after successful server response.
    private fun saveUserLocally() {
        // Implementation for saving the user data locally will go here.
    }

    // Resets the UI state to Nothing, allowing for retry attempts if needed.
    fun retry() {
        _uiState.value = SignupState.Nothing
    }
}

// Represents different states for the sign-up process.
sealed class SignupState {
    // Indicates the initial or reset state where no action is being taken.
    object Nothing : SignupState()
    // Represents the loading state while sign-up is in progress.
    object Loading : SignupState()
    // Represents a successful sign-up.
    object Success : SignupState()
    // Represents an error during sign-up, containing an error message.
    data class Error(val message: String) : SignupState()
}

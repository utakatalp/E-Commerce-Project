package com.example.e_commerce_project.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.e_commerce_project.ECommerceApplication
import com.example.e_commerce_project.data.DefaultAppContainer
import com.example.e_commerce_project.data.NetworkUserRepository
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.e_commerce_project.data.EmailOrPasswordErrorException
import com.example.e_commerce_project.data.FakeUserRepository
import com.example.e_commerce_project.data.UserRepository
import com.example.e_commerce_project.util.api.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
//import com.example.e_commerce_project.util.api.RetrofitInstance
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
open class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    internal val _loginUiState = MutableStateFlow(LoginUiState())
    open val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    private val _uiEffect = Channel<LoginUiEffect>()
    open val uiEffect = _uiEffect.receiveAsFlow()


    open fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EnterEmail -> onEmailChange(intent)
            is LoginIntent.EnterPassword -> onPasswordChange(intent)
            is LoginIntent.ShowPassword -> onPasswordToggle()
            is LoginIntent.SubmitLogin -> {
                performAuthWithExtension()
            }
        }
    }

    private fun onPasswordToggle() {
        _loginUiState.update { it.copy(showPassword = !loginUiState.value.showPassword) }
    }


    private fun performAuthWithExtension() {

        viewModelScope.launch {
            try {
                val loginRequest =
                    LoginRequest(loginUiState.value.email, loginUiState.value.password)
                val response = userRepository.signIn(loginRequest)

                response
                    .onSuccess {
                        _uiEffect.send(LoginUiEffect.NavigateHomeScreen)
                    }
                    .onFailure {
                        if (it is EmailOrPasswordErrorException) {
                            _loginUiState.update {
                                it.copy(wrongPasswordOrEmail = true)
                            }
                        }
                    }
            } catch (e: Exception) {
                _loginUiState.update {
                    it.copy(
                        errorMessage = e,
                    )
                }
            }


        }
    }

    private fun onPasswordChange(intent: LoginIntent.EnterPassword) {
        _loginUiState.update {
            it.copy(password = intent.password)
        }
    }

    private fun onEmailChange(intent: LoginIntent.EnterEmail) {
        _loginUiState.update {
            it.copy(email = intent.email)
        }
    }

    /*
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ECommerceApplication)
                val userRepository = application.container.userRepository
                LoginViewModel(userRepository)
            }
        }
    }

     */


}

class FakeLoginViewModel : LoginViewModel(FakeUserRepository()) {
    init {
        // Set a sample state for preview
        _loginUiState.value = LoginUiState(
            email = "preview@email.com",
            password = "123456",
            wrongPasswordOrEmail = true
        )
    }

    // Optionally override other behavior if needed
}
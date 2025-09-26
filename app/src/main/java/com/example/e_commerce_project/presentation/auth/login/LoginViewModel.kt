package com.example.e_commerce_project.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.data.remote.dto.response.LoginRequest
import com.example.e_commerce_project.data.repository.EmailOrPasswordErrorException
import com.example.e_commerce_project.domain.repository.UserRepository
import com.example.e_commerce_project.presentation.navigation.Home
import com.example.e_commerce_project.presentation.navigation.NavigationEffect
import com.example.e_commerce_project.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _navEffect = Channel<NavigationEffect>()
    open val navEffect = _navEffect.receiveAsFlow()


    open fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EnterEmail -> onEmailChange(intent)
            is LoginIntent.EnterPassword -> onPasswordChange(intent)
            is LoginIntent.ShowPassword -> onPasswordToggle()
            is LoginIntent.SubmitLogin -> { performAuthWithExtension() }
            is LoginIntent.ClickForgotPassword -> { navigateToForgotPassword() }
        }
    }

    private fun navigateToForgotPassword() {
        viewModelScope.launch {
            _navEffect.send(NavigationEffect(Route.ForgotPassword))
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
                        _navEffect.send(NavigationEffect(Home))
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
}

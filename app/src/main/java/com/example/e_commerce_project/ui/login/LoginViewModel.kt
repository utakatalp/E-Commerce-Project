package com.example.e_commerce_project.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.util.api.AuthRequest
import com.example.e_commerce_project.util.api.LoginRequest
import com.example.e_commerce_project.util.api.RetrofitInstance
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    val _uiEffect = Channel<LoginUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()


    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EnterEmail -> onEmailChange(intent)
            is LoginIntent.EnterPassword -> onPasswordChange(intent)
            is LoginIntent.SubmitLogin -> {
                val loginRequest =
                    LoginRequest(loginUiState.value.email, loginUiState.value.password)
                performAuthWithExtension(loginRequest)
            }
        }
    }


    private fun performAuthWithExtension(loginRequest: LoginRequest) {

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apiInterface.signIn(loginRequest)

                if (response.isSuccessful && response.body()?.status == 200) {
                    _uiEffect.send(LoginUiEffect.NavigateHomeScreen)
                } else {
                    _loginUiState.update {
                        it.copy(wrongPasswordOrEmail = true)
                    }
                }
            } catch (e: Exception){
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
package com.example.e_commerce_project.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()


    fun onIntent(intent: LoginIntent) {
        when(intent) {
            is LoginIntent.EnterEmail -> {
                _loginUiState.update {
                    it.copy(email = intent.email)
                }
            }
            is LoginIntent.EnterPassword -> {
                _loginUiState.update {
                    it.copy(password = intent.password)
                }
            }
            LoginIntent.SubmitLogin -> { }
        }
    }


}
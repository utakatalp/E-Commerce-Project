package com.example.e_commerce_project.ui.forgotpassword

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class ForgotPasswordViewModel : ViewModel() {

    private val _forgotPasswordUiState = MutableStateFlow(ForgotPasswordUiState())
    val forgotPasswordUiState: StateFlow<ForgotPasswordUiState> = _forgotPasswordUiState.asStateFlow()

    fun onIntent(intent: ForgotPasswordIntent) {
        when (intent) {
            is ForgotPasswordIntent.EnterEmail -> onEmailChange(intent)
            is ForgotPasswordIntent.SubmitForgotPassword -> {}
        }
    }

    private fun onEmailChange(intent: ForgotPasswordIntent.EnterEmail) {
        _forgotPasswordUiState.update {
            it.copy(email = intent.email)
        }
    }

}
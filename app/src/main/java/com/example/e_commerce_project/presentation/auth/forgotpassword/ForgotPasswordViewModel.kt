package com.example.e_commerce_project.presentation.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.DalmarScreen
import com.example.e_commerce_project.NavigationEffect
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
class ForgotPasswordViewModel @Inject constructor() : ViewModel() {

    private val _forgotPasswordUiState = MutableStateFlow(ForgotPasswordUiState())
    val forgotPasswordUiState: StateFlow<ForgotPasswordUiState> = _forgotPasswordUiState.asStateFlow()

    private val _navEffect = Channel<NavigationEffect>()
    val navEffect = _navEffect.receiveAsFlow()

    fun onIntent(intent: ForgotPasswordIntent) {
        when (intent) {
            is ForgotPasswordIntent.EnterEmail -> onEmailChange(intent)
            is ForgotPasswordIntent.SubmitForgotPassword -> { }
            is ForgotPasswordIntent.NavigatoToLogin -> navigateToLogin()

        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            _navEffect.send(NavigationEffect(DalmarScreen.LOGIN.name))
        }
    }

    private fun onEmailChange(intent: ForgotPasswordIntent.EnterEmail) {
        _forgotPasswordUiState.update {
            it.copy(email = intent.email)
        }
    }

}
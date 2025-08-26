package com.example.e_commerce_project.presentation.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val wrongPasswordOrEmail: Boolean = false,
    val errorMessage: Exception? = null,
    val showPassword: Boolean = false
)
sealed interface LoginUiEffect {
    data object NavigateToHome: LoginUiEffect
    data object NavigateToForgotPassword: LoginUiEffect

}

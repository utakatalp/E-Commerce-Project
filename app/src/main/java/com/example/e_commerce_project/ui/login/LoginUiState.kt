package com.example.e_commerce_project.ui.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val wrongPasswordOrEmail: Boolean = false,
    val errorMessage: Exception? = null,
    val showPassword: Boolean = false
)
sealed interface LoginUiEffect {
    data object NavigateHomeScreen: LoginUiEffect
}

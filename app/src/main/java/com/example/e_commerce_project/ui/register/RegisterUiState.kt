package com.example.e_commerce_project.ui.register

import com.example.e_commerce_project.util.password.ValidationResult

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val warning: List<ValidationResult> = emptyList(),
    val errorMessage: Exception? = null,
    var showPassword: Boolean = false
)
sealed interface RegisterUiEffect {
    data object NavigateHomeScreen: RegisterUiEffect
}

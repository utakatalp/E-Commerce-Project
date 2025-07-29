package com.example.e_commerce_project.ui.register

import com.example.e_commerce_project.util.password.ValidationResult

data class RegisterUiState(
    val name: String = "",
    val surname: String = "",
    var email: String = "",
    val password: String = "",
    val warning: List<ValidationResult> = emptyList()

)

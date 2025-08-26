package com.example.e_commerce_project.data.remote.dto.response


data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val phone: String,
    val address: String? = null
)


data class AuthResponse(
    val userId: String?,
    val status: Int,
    val message: String
)

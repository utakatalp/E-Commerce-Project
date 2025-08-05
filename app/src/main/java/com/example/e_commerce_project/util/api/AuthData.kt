package com.example.e_commerce_project.util.api


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


data class ResponseData(
    val userId: String?,
    val status: Int,
    val message: String
)

package com.example.e_commerce_project.data.remote.dto

data class UserDto(
    val userId: String,
    val email: String,
    val name: String,
    val phone: String,
)
data class UserResponseDto(
    val user: UserDto,
    val status: Int,
    val message: String
)

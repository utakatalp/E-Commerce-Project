package com.example.e_commerce_project.domain.repository

import com.example.e_commerce_project.data.remote.dto.response.LoginRequest
import com.example.e_commerce_project.data.remote.dto.response.RegisterRequest
import com.example.e_commerce_project.domain.model.User


interface UserRepository {
    suspend fun signIn(loginRequest: LoginRequest): Result<Unit>
    suspend fun signUp(registerRequest: RegisterRequest): Result<Unit>
    suspend fun logOut(): Result<Unit>
    suspend fun getUser(userId: String, store: String = "non-necessary"): Result<User>
}


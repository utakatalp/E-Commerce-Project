package com.example.e_commerce_project.domain.repository

interface UserPreferencesRepository {
    suspend fun saveUserId(token: String)
    suspend fun getUserId(): String?
    suspend fun clearUserId()
}
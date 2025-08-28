package com.example.e_commerce_project.data.repository

import android.util.Log
import com.example.e_commerce_project.data.local.PreferencesDataSource
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
import jakarta.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val local: PreferencesDataSource
) : UserPreferencesRepository {
    override suspend fun saveUserId(token: String) {
        local.saveUserId(token)
    }

    override suspend fun getUserId(): String? {
        val userId = local.getUserId()
        Log.d("UserPreferencesRepository", "Retrieved user ID: $userId")
        return local.getUserId()
    }

    override suspend fun clearUserId() {
        Log.d("UserPreferencesRepository", "Clearing user ID")
        local.clearUserId()
    }


}
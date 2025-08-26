package com.example.e_commerce_project.data.local

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject
import androidx.core.content.edit

class PreferencesDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun saveUserId(token: String) {
        Log.d("PreferencesDataSource", "Saving user ID: $token")
        sharedPreferences.edit { putString("USER_ID", token) }
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("USER_ID", null)
    }

    fun clearUserId() {
        sharedPreferences.edit { remove("USER_ID") }
    }

}
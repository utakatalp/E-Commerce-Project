package com.example.e_commerce_project.presentation.main.profile

import com.example.e_commerce_project.domain.model.Address
import com.example.e_commerce_project.domain.model.User

sealed interface ProfileUiState {
    data object Loading : ProfileUiState
    data class Success(
        val user: User,
        val addresses: List<Address>,
        val isEditingProfile: Boolean = false,
        val isChangingPassword: Boolean = false,
        val isAddingAddress: Boolean = false
    ) : ProfileUiState

    data class Empty(
        val message: String = "No profile data found."
    ) : ProfileUiState

    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : ProfileUiState
}


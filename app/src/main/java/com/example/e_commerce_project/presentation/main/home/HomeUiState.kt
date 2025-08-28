package com.example.e_commerce_project.presentation.main.home

import com.example.e_commerce_project.domain.model.Store
import com.example.e_commerce_project.domain.model.User


sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val user: User,
        val stores: List<Store>,
    ) : HomeUiState
    data class Empty(
        val message: String = "No data has been found."
    ) : HomeUiState
    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : HomeUiState
}


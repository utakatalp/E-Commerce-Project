package com.example.e_commerce_project.presentation.main.cart

import com.example.e_commerce_project.domain.model.Product

sealed interface CartUiState {
    data object Loading : CartUiState
    data class Success(
        val cartProducts: List<Product>,
        val favoritesProducts: List<Product>,
    ) : CartUiState

    data class Empty(
        val message: String = "No data has been found."
    ) : CartUiState

    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : CartUiState
}
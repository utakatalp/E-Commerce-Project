package com.example.e_commerce_project.presentation.main.productdetail

import com.example.e_commerce_project.domain.model.Product

sealed interface ProductDetailUiState {
    data object Loading : ProductDetailUiState
    data class Success(
        val product: Product,
    ) : ProductDetailUiState

    data class Empty(
        val message: String = "No data has been found."
    ) : ProductDetailUiState

    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : ProductDetailUiState
}
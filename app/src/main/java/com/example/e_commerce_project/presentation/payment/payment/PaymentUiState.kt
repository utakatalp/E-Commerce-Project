package com.example.e_commerce_project.presentation.payment.payment

import com.example.e_commerce_project.domain.model.Address
import com.example.e_commerce_project.domain.model.AddressWithCheck
import com.example.e_commerce_project.domain.model.User
import com.example.e_commerce_project.presentation.main.home.HomeUiState

sealed interface PaymentUiState {
    data object Loading : PaymentUiState
    data class Success(
        val addresses: List<AddressWithCheck>,
    ) : PaymentUiState

    data class Empty(
        val message: String = "No data has been found."
    ) : PaymentUiState

    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : PaymentUiState
}
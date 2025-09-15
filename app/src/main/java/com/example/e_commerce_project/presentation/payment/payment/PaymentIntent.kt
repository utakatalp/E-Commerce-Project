package com.example.e_commerce_project.presentation.payment.payment

sealed interface PaymentIntent {
    object OnPaymentClick : PaymentIntent
    data class  SelectAddress(val index: Int): PaymentIntent
}
package com.example.e_commerce_project.presentation.main.cart

sealed interface CartIntent {
    data class deleteFromCart(val productId: Int) : CartIntent
    data class deleteFromFavorites(val productId: Int) : CartIntent
    data class onProductClick(val id: String, val storeName: String) : CartIntent
    object RefreshCart : CartIntent
    object Checkout : CartIntent

}
package com.example.e_commerce_project.presentation.main.productdetail

sealed interface ProductDetailIntent {
    data class AddToFavorite(val store: String, val productId: Int) : ProductDetailIntent
    data class AddToCart(val store: String, val productId: Int) : ProductDetailIntent
    data class DeleteFromCart(val productId: Int) : ProductDetailIntent
    data class DeleteFromFavorites(val productId: Int) : ProductDetailIntent
}
package com.example.e_commerce_project.presentation.main.home

import com.example.e_commerce_project.domain.model.Category

sealed interface HomeIntent {
    object onLogoutClick : HomeIntent
    data class onProductClick(val id: String, val storeName: String) : HomeIntent
    data class onAddToCartClick(val productId: Int, val storeName: String) : HomeIntent
    data class onFavoriteClick(val productId: Int, val storeName: String) : HomeIntent
    data class DeleteFromFavorites(val productId: Int) : HomeIntent
    data class Search(val query: String) : HomeIntent
    data class ExpandSearch(val expanded: Boolean) : HomeIntent
    data class CategoryClick(val category: Category) : HomeIntent
}
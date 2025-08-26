package com.example.e_commerce_project.presentation.main.home

sealed interface HomeIntent {
    object onLogoutClick : HomeIntent
    data class onProductClick(val id: String): HomeIntent
}
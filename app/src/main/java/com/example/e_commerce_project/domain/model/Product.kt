package com.example.e_commerce_project.domain.model

data class Product(
    val id: Int,
    val storeName: String,
    val title: String,
    val price: Double,
    val salePrice: Double,
    val description: String,
    val category: String,
    val images: List<String>,
    val rate: Double,
    val count: Int,
    val saleState: Boolean,
    var isFavorite: Boolean
)
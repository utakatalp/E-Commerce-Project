package com.example.e_commerce_project.domain.model

data class Store(
    val name: String,
    val products: List<Product>,
    val categories: List<Category>
)
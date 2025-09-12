package com.example.e_commerce_project.data.remote.dto

import com.example.e_commerce_project.domain.model.Product


data class ProductResponseDto(
    val product: ProductDto,
    val status: Int,
    val message: String
)
data class ProductsResponseDto(
    val products: List<ProductDto>,
    val status: Int,
    val message: String
)
data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val salePrice: Double,
    val description: String,
    val category: String,
    val imageOne: String,
    val imageTwo: String,
    val imageThree: String,
    val rate: Double,
    val count: Int,
    val saleState: Boolean
) {
    fun toDomain(storeName: String): Product {
        return Product(
            id = id,
            title = title,
            price = price,
            salePrice = salePrice,
            description = description,
            category = category,
            images = listOf(imageOne, imageTwo, imageThree),
            rate = rate,
            count = count,
            saleState = saleState,
            isFavorite = false,
            storeName = storeName
        )
    }
}
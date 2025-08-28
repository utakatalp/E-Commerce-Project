package com.example.e_commerce_project.data.remote.dto

import com.example.e_commerce_project.domain.model.Category

data class CategoriesResponseDto(
    val categories: List<CategoryDto>,
    val status: Int,
    val message: String
)

data class CategoryDto(
    val name: String,
    val image: String,
) {
    fun toDomain(): Category {
        return Category(
            name = name,
            image = image
        )
    }
}

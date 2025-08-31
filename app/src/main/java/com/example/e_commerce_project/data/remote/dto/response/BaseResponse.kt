package com.example.e_commerce_project.data.remote.dto.response

open class BaseResponse(
    val status: Int? = null,
    val message: String? = null
)

class AddToCartRequest(
    val userId: String,
    val productId: Int
)

class AddToFavoritesRequest(
    val userId: String,
    val productId: Int
)
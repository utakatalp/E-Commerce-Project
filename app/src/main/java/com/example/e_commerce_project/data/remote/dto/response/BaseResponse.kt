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

class DeleteFromCartRequest(
    val userId: String,
    val id: Int
)

class DeleteFromFavoritesRequest(
    val userId: String,
    val id: Int
)

class AddAddressRequest(
    val userId: String,
    val title: String,
    val address: String,
)

class DeleteFromAddressesRequest(
    val userId: String,
    val id: Int
)

class EditProfileRequest(
    val userId: String,
    val name: String,
    val phone: String,
    val address: String
)

class ChangePasswordRequest(
    val userId: String,
    val password: String
)
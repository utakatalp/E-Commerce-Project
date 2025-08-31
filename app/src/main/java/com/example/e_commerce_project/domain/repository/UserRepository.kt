package com.example.e_commerce_project.domain.repository

import com.example.e_commerce_project.data.remote.dto.response.AddAddressRequest
import com.example.e_commerce_project.data.remote.dto.response.AddToCartRequest
import com.example.e_commerce_project.data.remote.dto.response.AddToFavoritesRequest
import com.example.e_commerce_project.data.remote.dto.response.ChangePasswordRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromAddressesRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromCartRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromFavoritesRequest
import com.example.e_commerce_project.data.remote.dto.response.EditProfileRequest
import com.example.e_commerce_project.data.remote.dto.response.LoginRequest
import com.example.e_commerce_project.data.remote.dto.response.RegisterRequest
import com.example.e_commerce_project.domain.model.Address
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.domain.model.User


interface UserRepository {
    suspend fun signIn(loginRequest: LoginRequest): Result<Unit>
    suspend fun signUp(registerRequest: RegisterRequest): Result<Unit>
    suspend fun logOut(): Result<Unit>
    suspend fun getUser(userId: String, store: String = "non-necessary"): Result<User>
    suspend fun addToCart(store: String, addToCartRequest: AddToCartRequest): Result<String>
    suspend fun addToFavorites(
        store: String,
        addToFavoritesRequest: AddToFavoritesRequest
    ): Result<String>

    suspend fun getFavorites(userId: String, store: String = "non-necessary"): Result<List<Product>>
    suspend fun getCartProducts(
        userId: String,
        store: String = "non-necessary"
    ): Result<List<Product>>

    suspend fun deleteFromCart(
        store: String,
        deleteFromCartRequest: DeleteFromCartRequest
    ): Result<String>

    suspend fun clearCart(store: String, userId: String): Result<String>
    suspend fun clearFavorites(store: String, userId: String): Result<String>
    suspend fun deleteFromFavorites(
        store: String,
        deleteFromFavoritesRequest: DeleteFromFavoritesRequest
    ): Result<String>

    suspend fun addAddress(
        store: String = "non-necessary",
        addAddressRequest: AddAddressRequest
    ): Result<String>

    suspend fun getAddresses(userId: String, store: String = "non-necessary"): Result<List<Address>>
    suspend fun deleteFromAddresses(
        store: String = "non-necessary",
        deleteFromAddressesRequest: DeleteFromAddressesRequest
    ): Result<String>

    suspend fun clearAddresses(store: String = "non-necessary", userId: String): Result<String>
    suspend fun editProfile(
        store: String = "non-necessary",
        editProfileRequest: EditProfileRequest
    ): Result<String>

    suspend fun changePassword(
        store: String = "non-necessary",
        changePasswordRequest: ChangePasswordRequest
    ): Result<String>
}


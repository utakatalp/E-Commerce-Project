package com.example.e_commerce_project.data.repository

import android.util.Log
import com.example.e_commerce_project.data.mapper.toDomain
import com.example.e_commerce_project.data.remote.ApiInterface
import com.example.e_commerce_project.data.remote.dto.response.AddAddressRequest
import com.example.e_commerce_project.data.remote.dto.response.AddToCartRequest
import com.example.e_commerce_project.data.remote.dto.response.AddToFavoritesRequest
import com.example.e_commerce_project.data.remote.dto.response.ChangePasswordRequest
import com.example.e_commerce_project.data.remote.dto.response.ClearAddressesRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromAddressesRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromCartRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromFavoritesRequest
import com.example.e_commerce_project.data.remote.dto.response.EditProfileRequest
import com.example.e_commerce_project.data.remote.dto.response.LoginRequest
import com.example.e_commerce_project.data.remote.dto.response.RegisterRequest
import com.example.e_commerce_project.domain.model.Address
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.domain.model.User
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
import com.example.e_commerce_project.domain.repository.UserRepository
import javax.inject.Inject

class NetworkUserRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val userPreferencesRepository: UserPreferencesRepository
) : UserRepository {

    override suspend fun signIn(loginRequest: LoginRequest): Result<Unit> {

        val response = apiInterface.signIn(loginRequest)

        return if (response.isSuccessful && response.body()?.status == 200) {
            Log.d("NetworkUserRepository", "User logged in successfully")
            userPreferencesRepository.saveUserId(response.body()?.userId.toString())
            Result.success(Unit)
        } else if (response.isSuccessful && response.body()?.status == 400) {
            Result.failure(EmailOrPasswordErrorException())
        } else {
            Result.failure(Exception("Unexpected error occurred."))
        }
    }

    override suspend fun signUp(registerRequest: RegisterRequest): Result<Unit> {
        val response = apiInterface.signUp(registerRequest)

        return if (response.isSuccessful && response.body()?.status == 200) {
            userPreferencesRepository.saveUserId(response.body()?.userId.toString())
            Result.success(Unit)
        } else if (response.isSuccessful && response.body()?.status == 400) {
            Result.failure(ExistingUser())
        } else {
            Result.failure(Exception("Unexpected error occurred."))
        }

    }

    override suspend fun logOut(): Result<Unit> {
        userPreferencesRepository.clearUserId()
        return Result.success(Unit)
    }

    override suspend fun getUser(userId: String, store: String): Result<User> {
        val response = apiInterface.getUser(store, userId)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.user?.toDomain()!!)
        } else {
            Result.failure(Exception("Unexpected error occurred."))
        }
    }

    override suspend fun addToCart(
        store: String,
        addToCartRequest: AddToCartRequest
    ): Result<String> {
        val response = apiInterface.addToCart(store, addToCartRequest)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun addToFavorites(
        store: String,
        addToFavoritesRequest: AddToFavoritesRequest
    ): Result<String> {
        val response = apiInterface.addToFavorite(store, addToFavoritesRequest)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun getFavorites(
        userId: String,
        store: String
    ): Result<List<Product>> {
        val response = apiInterface.getFavorites(store, userId)
        return if (response.isSuccessful) {
            Result.success(response.body()?.products?.map { it.toDomain(store) }!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun getCartProducts(
        userId: String,
        store: String
    ): Result<List<Product>> {
        val response = apiInterface.getCartProducts(store, userId)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.products?.map { it.toDomain(store) }!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun deleteFromCart(
        store: String,
        deleteFromCartRequest: DeleteFromCartRequest
    ): Result<String> {
        val response = apiInterface.deleteFromCart(store, deleteFromCartRequest)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun clearCart(
        store: String,
        userId: String
    ): Result<String> {
        val response = apiInterface.clearCart(store, userId)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun clearFavorites(
        store: String,
        userId: String
    ): Result<String> {
        val response = apiInterface.clearFavorites(store, userId)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun deleteFromFavorites(
        store: String,
        deleteFromFavoritesRequest: DeleteFromFavoritesRequest
    ): Result<String> {
        val response = apiInterface.deleteFromFavorites(store, deleteFromFavoritesRequest)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun addAddress(
        store: String,
        addAddressRequest: AddAddressRequest
    ): Result<String> {
        val response = apiInterface.addAddress(store, addAddressRequest)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun getAddresses(
        userId: String,
        store: String
    ): Result<List<Address>> {
        val response = apiInterface.getAddresses(store, userId)
//        Log.d("NetworkUserRepository", "Response: ${response.body()}")
        return if (response.isSuccessful && response.body()?.status == 200) {
            val addresses = response.body()?.addresses?.map { it.toDomain() } ?: emptyList()
            Result.success(addresses)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun deleteFromAddresses(
        store: String,
        deleteFromAddressesRequest: DeleteFromAddressesRequest
    ): Result<String> {
        val response = apiInterface.deleteFromAddresses(store, deleteFromAddressesRequest)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun clearAddresses(
        store: String,
        userId: String
    ): Result<String> {
        val response = apiInterface.clearAddresses(store, ClearAddressesRequest(userId))
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }

    }

    override suspend fun editProfile(
        store: String,
        editProfileRequest: EditProfileRequest
    ): Result<String> {
        val response = apiInterface.editProfile(store, editProfileRequest)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }

    override suspend fun changePassword(
        store: String,
        changePasswordRequest: ChangePasswordRequest
    ): Result<String> {
        val response = apiInterface.changePassword(store, changePasswordRequest)
        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(response.body()?.message!!)
        } else {
            Result.failure(Exception(response.body()?.message))
        }
    }
}

class ExistingUser : Throwable()
class EmailOrPasswordErrorException() : Throwable()
/*
class FakeUserRepository : UserRepository {
    override suspend fun signIn(loginRequest: LoginRequest): Result<Unit> {
        // Always fail for preview example
        return Result.failure(EmailOrPasswordErrorException())
    }

    override suspend fun signUp(registerRequest: RegisterRequest): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun logOut(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userId: String): Result<User> {
        TODO("Not yet implemented")
    }
}

 */
package com.example.e_commerce_project.data.repository

import android.util.Log
import com.example.e_commerce_project.data.mapper.toDomain
import com.example.e_commerce_project.data.remote.ApiInterface
import com.example.e_commerce_project.data.remote.dto.response.AddToCartRequest
import com.example.e_commerce_project.data.remote.dto.response.LoginRequest
import com.example.e_commerce_project.data.remote.dto.response.RegisterRequest
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
        if (response.isSuccessful && response.body()?.status == 200) {
            return Result.success(response.body()?.user?.toDomain()!!)
        } else {
            return Result.failure(Exception("Unexpected error occurred."))
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
        addToCartRequest: AddToCartRequest
    ): Result<String> {
        val response = apiInterface.addToCart(store, addToCartRequest)
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
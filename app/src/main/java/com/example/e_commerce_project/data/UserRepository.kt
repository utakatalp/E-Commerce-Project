package com.example.e_commerce_project.data

import com.example.e_commerce_project.util.api.ApiInterface
import com.example.e_commerce_project.util.api.LoginRequest
import com.example.e_commerce_project.util.api.RegisterRequest
import com.example.e_commerce_project.util.api.ResponseData
import retrofit2.Response
import javax.inject.Inject


interface UserRepository {
    suspend fun signIn(loginRequest: LoginRequest): Result<Unit>
    suspend fun signUp(registerRequest: RegisterRequest): Result<Unit>
}

class NetworkUserRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : UserRepository {

    override suspend fun signIn(loginRequest: LoginRequest): Result<Unit> {
        val response = apiInterface.signIn(loginRequest)

        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(Unit)
        } else if (response.isSuccessful && response.body()?.status == 400) {
            Result.failure(EmailOrPasswordErrorException())
        } else {
            Result.failure(error("Unexpected error occured."))
        }
    }

    override suspend fun signUp(registerRequest: RegisterRequest): Result<Unit> {
        val response = apiInterface.signUp(registerRequest)

        return if (response.isSuccessful && response.body()?.status == 200) {
            Result.success(Unit)
        } else if (response.isSuccessful && response.body()?.status == 400) {
            Result.failure(ExistingUser())
        } else {
            Result.failure(error("Unexpected error occured."))
        }

    }
}
class ExistingUser : Throwable()
class EmailOrPasswordErrorException() : Throwable()
class FakeUserRepository : UserRepository {
    override suspend fun signIn(loginRequest: LoginRequest): Result<Unit> {
        // Always fail for preview example
        return Result.failure(EmailOrPasswordErrorException())
    }

    override suspend fun signUp(registerRequest: RegisterRequest): Result<Unit> {
        return Result.success(Unit)
    }
}
package com.example.e_commerce_project.data

import com.example.e_commerce_project.util.api.ApiInterface
import com.example.e_commerce_project.util.api.LoginRequest
import com.example.e_commerce_project.util.api.RegisterRequest
import com.example.e_commerce_project.util.api.ResponseData
//import com.example.e_commerce_project.util.api.RetrofitInstance
import retrofit2.Response


interface UserRepository {
    suspend fun signIn(loginRequest: LoginRequest): Response<ResponseData>
    suspend fun signUp(registerRequest: RegisterRequest): Response<ResponseData>
}

class NetworkUserRepository(
    private val apiInterface: ApiInterface
): UserRepository{
    override suspend fun signIn(loginRequest: LoginRequest): Response<ResponseData> {
        return apiInterface.signIn(loginRequest)
    }

    override suspend fun signUp(registerRequest: RegisterRequest): Response<ResponseData> {
        return apiInterface.signUp(registerRequest)
    }



}
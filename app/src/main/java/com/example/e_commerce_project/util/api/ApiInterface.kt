package com.example.e_commerce_project.util.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface { // coroutine with retrofit, add suspend
    @POST("sign_up")
    suspend fun signUp(@Body request: RegisterRequest): Response<ResponseData>
    @POST("sign_in")
    suspend fun signIn(@Body request: LoginRequest): Response<ResponseData>
}
package com.example.e_commerce_project.data.remote

import com.example.e_commerce_project.data.remote.dto.CategoriesResponseDto
import com.example.e_commerce_project.data.remote.dto.ProductResponseDto
import com.example.e_commerce_project.data.remote.dto.ProductsResponseDto
import com.example.e_commerce_project.data.remote.dto.UserResponseDto
import com.example.e_commerce_project.data.remote.dto.response.AddToCartRequest
import com.example.e_commerce_project.data.remote.dto.response.AddToFavoritesRequest
import com.example.e_commerce_project.data.remote.dto.response.AuthResponse
import com.example.e_commerce_project.data.remote.dto.response.BaseResponse
import com.example.e_commerce_project.data.remote.dto.response.LoginRequest
import com.example.e_commerce_project.data.remote.dto.response.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface { // coroutine with retrofit, add suspend
    @POST("sign_up")
    suspend fun signUp(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("sign_in")
    suspend fun signIn(@Body request: LoginRequest): Response<AuthResponse>

    @GET("get_user")
    suspend fun getUser(
        @Header("store") store: String,
        @Query("userId") userId: String
    ): Response<UserResponseDto>

    @GET("get_products")
    suspend fun getProducts(@Header("store") store: String): Response<ProductsResponseDto>

    @GET("get_categories")
    suspend fun getCategories(@Header("store") store: String): Response<CategoriesResponseDto>

    @GET("get_product_detail")
    suspend fun getProductDetail(
        @Header("store") store: String,
        @Query("id") id: Int
    ): Response<ProductResponseDto>

    @POST("add_to_cart")
    suspend fun addToCart(
        @Header("store") store: String,
        @Body request: AddToCartRequest
    ): Response<BaseResponse>

    @POST("add_to_favorite")
    suspend fun addToFavorite(
        @Header("store") store: String,
        @Body request: AddToFavoritesRequest
    ): Response<BaseResponse>
}
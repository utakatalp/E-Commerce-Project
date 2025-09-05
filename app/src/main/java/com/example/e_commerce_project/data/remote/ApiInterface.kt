package com.example.e_commerce_project.data.remote

import com.example.e_commerce_project.data.remote.dto.AddressResponseDto
import com.example.e_commerce_project.data.remote.dto.CategoriesResponseDto
import com.example.e_commerce_project.data.remote.dto.ProductResponseDto
import com.example.e_commerce_project.data.remote.dto.ProductsResponseDto
import com.example.e_commerce_project.data.remote.dto.UserResponseDto
import com.example.e_commerce_project.data.remote.dto.response.AddAddressRequest
import com.example.e_commerce_project.data.remote.dto.response.AddToCartRequest
import com.example.e_commerce_project.data.remote.dto.response.AddToFavoritesRequest
import com.example.e_commerce_project.data.remote.dto.response.AuthResponse
import com.example.e_commerce_project.data.remote.dto.response.BaseResponse
import com.example.e_commerce_project.data.remote.dto.response.ChangePasswordRequest
import com.example.e_commerce_project.data.remote.dto.response.ClearAddressesRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromAddressesRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromCartRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromFavoritesRequest
import com.example.e_commerce_project.data.remote.dto.response.EditProfileRequest
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

    @POST("add_to_favorites")
    suspend fun addToFavorite(
        @Header("store") store: String,
        @Body request: AddToFavoritesRequest
    ): Response<BaseResponse>

    @GET("get_favorites")
    suspend fun getFavorites(
        @Header("store") store: String,
        @Query("userId") userId: String
    ): Response<ProductsResponseDto>

    @GET("get_cart_products")
    suspend fun getCartProducts(
        @Header("store") store: String,
        @Query("userId") userId: String
    ): Response<ProductsResponseDto>

    @POST("delete_from_cart")
    suspend fun deleteFromCart(
        @Header("store") store: String,
        @Body request: DeleteFromCartRequest
    ): Response<BaseResponse>

    @POST("clear_cart")
    suspend fun clearCart(
        @Header("store") store: String,
        @Body userId: String
    ): Response<BaseResponse>

    @POST("delete_from_favorites")
    suspend fun deleteFromFavorites(
        @Header("store") store: String,
        @Body request: DeleteFromFavoritesRequest
    ): Response<BaseResponse>

    @POST("clear_favorites")
    suspend fun clearFavorites(
        @Header("store") store: String,
        @Body userId: String
    ): Response<BaseResponse>

    @POST("add_address")
    suspend fun addAddress(
        @Header("store") store: String,
        @Body request: AddAddressRequest
    ): Response<BaseResponse>

    @GET("get_addresses")
    suspend fun getAddresses(
        @Header("store") store: String,
        @Query("userId") userId: String
    ): Response<AddressResponseDto>

    @POST("delete_from_addresses")
    suspend fun deleteFromAddresses(
        @Header("store") store: String,
        @Body request: DeleteFromAddressesRequest
    ): Response<BaseResponse>

    @POST("clear_addresses")
    suspend fun clearAddresses(
        @Header("store") store: String,
        @Body request: ClearAddressesRequest
    ): Response<BaseResponse>

    @POST("edit_profile")
    suspend fun editProfile(
        @Header("store") store: String,
        @Body request: EditProfileRequest
    ): Response<BaseResponse>

    @POST("change_password")
    suspend fun changePassword(
        @Header("store") store: String,
        @Body request: ChangePasswordRequest
    ): Response<BaseResponse>

    //    @GET("search_product")
//    suspend fun searchProduct(
//        @Header("store") store: String,
//        @Query("query") query: String
//    )
    @GET("get_products_by_category")
    suspend fun getProductsByCategory(
        @Header("store") store: String,
        @Query("category") category: String
    ): Response<ProductsResponseDto>

}
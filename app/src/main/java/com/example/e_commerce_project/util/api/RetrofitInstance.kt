package com.example.e_commerce_project.util.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.getValue

//object RetrofitInstance {
//
//    private val retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://api.canerture.com/ecommerce/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    val apiInterface: ApiInterface by lazy {
//        retrofit.create(ApiInterface::class.java)
//    }
//}
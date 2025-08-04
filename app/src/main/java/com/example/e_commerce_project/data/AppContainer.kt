package com.example.e_commerce_project.data

import com.example.e_commerce_project.util.api.ApiInterface
//import com.example.e_commerce_project.util.api.RetrofitInstance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl = "https://api.canerture.com/ecommerce/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiInterface: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(apiInterface)
    }
}
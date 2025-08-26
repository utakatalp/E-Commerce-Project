package com.example.e_commerce_project.domain.repository

import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.domain.model.Store

interface StoreRepository {
    suspend fun getProducts(storeName: String): Result<List<Product>>
    suspend fun getStore(storeName: String): Result<Store>
}
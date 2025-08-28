package com.example.e_commerce_project.data.repository

import android.util.Log
import com.example.e_commerce_project.data.remote.ApiInterface
import com.example.e_commerce_project.domain.model.Category
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.domain.model.Store
import com.example.e_commerce_project.domain.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : StoreRepository {
    override suspend fun getProducts(storeName: String): Result<List<Product>> {
        val response = apiInterface.getProducts(storeName)

        if (response.isSuccessful && response.body()?.status == 200) {
            val products = response.body()?.products?.map { it.toDomain() }
            return Result.success(products!!)
        } else {
            return Result.failure(Exception("Unexpected error occurred."))
        }
    }

    override suspend fun getStore(storeName: String): Result<Store> {
        Log.d("unexpected", "getting in getStore")
        return withContext(context = Dispatchers.IO) {
            coroutineScope {
                val deferredProducts = async { getProducts(storeName) }
                val deferredCategories = async { getCategories(storeName) }
                Result.success(
                    Store(
                        storeName,
                        deferredProducts.await().getOrThrow(),
                        deferredCategories.await().getOrThrow()
                    )
                )
            }
        }
    }

    override suspend fun getCategories(storeName: String): Result<List<Category>> {
        val response = apiInterface.getCategories(storeName)
        if (response.isSuccessful && response.body()?.status == 200) {
            val categories = response.body()?.categories?.map { it.toDomain() }
            Log.d("unexpected", categories.toString())
            return Result.success(categories!!)
        } else {
            return Result.failure(Exception("Unexpected error occurred."))
        }
    }

    override suspend fun getProductDetail(
        storeName: String,
        productId: Int
    ): Result<Product> {
        val response = apiInterface.getProductDetail(storeName, productId)
        return if (response.isSuccessful && response.body()?.status == 200) {
            val product = response.body()?.product?.toDomain()
            Log.d("asd", response.body()?.product.toString())
            Log.d("prod", product.toString())
            Result.success(product!!)
        } else {
            Result.failure(Exception("Unexpected error occurred."))
        }
    }
}
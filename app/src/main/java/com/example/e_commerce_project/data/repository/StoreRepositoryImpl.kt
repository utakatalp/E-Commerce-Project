package com.example.e_commerce_project.data.repository

import com.example.e_commerce_project.data.remote.ApiInterface
import com.example.e_commerce_project.domain.model.Product
import com.example.e_commerce_project.domain.model.Store
import com.example.e_commerce_project.domain.repository.StoreRepository
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
) : StoreRepository {
    override suspend fun getProducts(storeName: String): Result<List<Product>> {
        val response = apiInterface.getProducts(storeName)

        if(response.isSuccessful && response.body()?.status == 200) {
            val products = response.body()?.products?.map { it.toDomain()
             }
            return Result.success(products!!)
        } else {
            return Result.failure(Exception("Unexpected error occurred."))
        }
    }

    override suspend fun getStore(storeName: String): Result<Store> {
        getProducts(storeName).onSuccess { products ->
            return Result.success(Store(storeName, products))
        }
        return Result.failure(Exception("Unexpected error occurred."))


    }
}
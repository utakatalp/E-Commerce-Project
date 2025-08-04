package com.example.e_commerce_project.util.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T>Call<T>.executeAuth(
    onLoading: (Boolean) -> Unit, onSuccess: (T) -> Unit
) {
    onLoading(true)
    this.enqueue(object : Callback<T?> {
        override fun onResponse(call: Call<T?>, response: Response<T?>) {
            onLoading(false)
            try {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {

                }
            } catch (e: Exception) {

            }
        }

        override fun onFailure(call: Call<T?>, t: Throwable) {
            onLoading(false)

        }
    })
}
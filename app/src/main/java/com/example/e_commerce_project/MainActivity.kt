package com.example.e_commerce_project

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import retrofit2.Callback
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.e_commerce_project.util.api.LoginRequest


import com.example.e_commerce_project.ui.theme.ECommerceProjectTheme
import retrofit2.Call
import retrofit2.Response

class MainActivity : ComponentActivity() {

    // private var responseDataState by mutableStateOf<ResponseData?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // getData()
        setContent {
            ECommerceProjectTheme {

                DalmarApp()
                }
            }
        }

    /*private fun getData() {
        isLoading = true
        try {
            val loginRequest = LoginRequest(email = "alp1", password = "alp2")
            RetrofitInstance.apiInterface.getData(loginRequest)
                .enqueue(object : Callback<ResponseData?> {
                    override fun onResponse(call: Call<ResponseData?>, response: Response<ResponseData?>) {
                        isLoading = false
                        try {
                            if (response.isSuccessful && response.body() != null) {
                                responseDataState = response.body()
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Response unsuccessful or empty",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@MainActivity,
                                "Exception in onResponse: ${e.localizedMessage}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseData?>, t: Throwable) {
                        isLoading = false
                        Toast.makeText(
                            this@MainActivity,
                            "Network call failed: ${t.localizedMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        } catch (e: Exception) {
            isLoading = false
            Toast.makeText(this@MainActivity, "Exception in getData: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
        }
    }

     */

}



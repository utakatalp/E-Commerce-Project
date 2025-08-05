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
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Response
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECommerceProjectTheme {

                DalmarApp()
                }
            }
        }
}



package com.example.e_commerce_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.e_commerce_project.ui.theme.ECommerceProjectTheme

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



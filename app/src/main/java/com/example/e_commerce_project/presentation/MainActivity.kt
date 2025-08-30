package com.example.e_commerce_project.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.e_commerce_project.presentation.navigation.AppNavigation
//import com.example.e_commerce_project.DalmarApp
import com.example.e_commerce_project.presentation.ui.theme.ECommerceProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {

        }
        setContent {
            ECommerceProjectTheme {
                AppNavigation()
            }
        }
    }
}
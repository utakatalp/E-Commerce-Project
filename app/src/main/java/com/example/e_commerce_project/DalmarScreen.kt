package com.example.e_commerce_project

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_project.ui.screens.ForgotPasswordScreen
import com.example.e_commerce_project.ui.screens.LoginScreen

import com.example.e_commerce_project.ui.screens.RegisterScreen
import com.example.e_commerce_project.ui.screens.WelcomeScreen

enum class DalmarScreen() {
    Welcome,
    Register,
    Login,
    ForgotPassword
}



@Composable
fun DalmarApp(
    navController: NavHostController = rememberNavController()
) {


    NavHost(
        navController = navController,
        startDestination = DalmarScreen.Welcome.name,

        ) {
        composable(route = DalmarScreen.Welcome.name) {
            WelcomeScreen(
                onRegisterButtonClicked = { navController.navigate(DalmarScreen.Register.name) },
                onLoginButtonClicked = { navController.navigate(DalmarScreen.Login.name) }
            )
        }
        composable(route = DalmarScreen.Register.name) {
            RegisterScreen(onNavigateBackPressed = { navController.navigate(DalmarScreen.Welcome.name) })
        }
        composable(route = DalmarScreen.Login.name) {
            LoginScreen(
                onForgotPasswordButtonClicked = { navController.navigate(DalmarScreen.ForgotPassword.name) },
                onNavigateBackPressed = { navController.navigate(DalmarScreen.Welcome.name) }
            )
        }
        composable(route = DalmarScreen.ForgotPassword.name) {
            ForgotPasswordScreen(onBackToLoginButtonClicked = { navController.navigate(DalmarScreen.Login.name) })
        }

    }


}

package com.example.e_commerce_project

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_project.ui.forgotpassword.ForgotPasswordScreen
import com.example.e_commerce_project.ui.login.LoginScreen

import com.example.e_commerce_project.ui.register.RegisterScreen
import com.example.e_commerce_project.ui.welcome.WelcomeScreen

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

package com.example.e_commerce_project

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_project.presentation.MainNavigation
import com.example.e_commerce_project.presentation.auth.forgotpassword.ForgotPasswordScreen
import com.example.e_commerce_project.presentation.auth.forgotpassword.ForgotPasswordViewModel
import com.example.e_commerce_project.presentation.auth.login.LoginScreen
import com.example.e_commerce_project.presentation.auth.login.LoginViewModel
import com.example.e_commerce_project.presentation.auth.register.RegisterScreen
import com.example.e_commerce_project.presentation.auth.register.RegisterViewModel
import com.example.e_commerce_project.presentation.auth.welcome.WelcomeScreen
import com.example.e_commerce_project.presentation.auth.welcome.WelcomeViewModel
import com.example.e_commerce_project.presentation.splash.SplashScreen
import com.example.e_commerce_project.presentation.splash.SplashViewModel
import kotlinx.coroutines.flow.Flow

enum class DalmarScreen() {
    SPLASH,
    WELCOME,
    REGISTER,
    LOGIN,
    HOME,
    FORGOT_PASSWORD
}

data class NavigationEffect(val route: String)


val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("NavController not provided")
}

@Composable
fun DalmarApp(
    navController: NavHostController = rememberNavController(),
) {
    CompositionLocalProvider(LocalNavController provides navController) {

        NavHost(
            navController = navController,
            startDestination = DalmarScreen.SPLASH.name,
        ) {
            composable(route = DalmarScreen.SPLASH.name) {
                val viewModel = hiltViewModel<SplashViewModel>()
                SplashScreen()
                NavigationCollector(viewModel.navEffect, clearBackStack = true)
            }
            composable(route = DalmarScreen.WELCOME.name) {
                val viewModel = hiltViewModel<WelcomeViewModel>()
                WelcomeScreen(
                    onIntent = { viewModel.onIntent(it) }
                )
                NavigationCollector(viewModel.navEffect)
            }
            composable(route = DalmarScreen.REGISTER.name) {
                val viewModel = hiltViewModel<RegisterViewModel>()
                val uiState by viewModel.registerUiState.collectAsState()
                RegisterScreen(
                    onIntent = { viewModel.onIntent(it) },
                    uiState = uiState
                )
                NavigationCollector(viewModel.navEffect, clearBackStack = true)
            }
            composable(route = DalmarScreen.LOGIN.name) {
                val viewModel = hiltViewModel<LoginViewModel>()
                val uiState by viewModel.loginUiState.collectAsState()
                LoginScreen(
                    onIntent = { viewModel.onIntent(it) },
                    uiState = uiState,
                )
                NavigationCollector(
                    viewModel.navEffect,
                    clearBackStack = true
                )
            }
            composable(route = DalmarScreen.FORGOT_PASSWORD.name) {
                val viewModel = hiltViewModel<ForgotPasswordViewModel>()
                val uiState by viewModel.forgotPasswordUiState.collectAsState()
                ForgotPasswordScreen(
                    onIntent = { viewModel.onIntent(it) },
                    uiState = uiState
                )
                NavigationCollector(
                    viewModel.navEffect
                )
            }
            composable(route = DalmarScreen.HOME.name) {
                MainNavigation(parentNavController = navController)

            }
        }

    }

}

@Composable
fun NavigationCollector(
    navigationEffect: Flow<NavigationEffect>,
    clearBackStack: Boolean = false
) {
    val navController = LocalNavController.current

    LaunchedEffect(navController) {
        navController.currentBackStack.collect { backStack ->
            // backStack, NavBackStackEntry listesidir.
            // Daha okunaklı bir log için her bir entry'nin rotasını alalım.
            val routes = backStack.joinToString(" -> ") { it.destination.route ?: "null_route" }
            Log.d("BackstackLogger", "Current Backstack: [ $routes ]")
        }
    }

    LaunchedEffect(Unit) {
        navigationEffect.collect {
            navController.navigate(it.route) {
                if (clearBackStack) {
                    val destRoute =
                        navController.currentBackStack.value[1].destination.route.toString()
                    popUpTo(destRoute) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

package com.example.e_commerce_project.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.example.e_commerce_project.presentation.auth.forgotpassword.ForgotPasswordScreen
import com.example.e_commerce_project.presentation.auth.forgotpassword.ForgotPasswordViewModel
import com.example.e_commerce_project.presentation.auth.login.LoginScreen
import com.example.e_commerce_project.presentation.auth.login.LoginViewModel
import com.example.e_commerce_project.presentation.auth.register.RegisterScreen
import com.example.e_commerce_project.presentation.auth.register.RegisterViewModel
import com.example.e_commerce_project.presentation.auth.welcome.WelcomeScreen
import com.example.e_commerce_project.presentation.auth.welcome.WelcomeViewModel
import com.example.e_commerce_project.presentation.main.cart.CartScreen
import com.example.e_commerce_project.presentation.main.cart.CartViewModel
import com.example.e_commerce_project.presentation.main.home.HomeScreen
import com.example.e_commerce_project.presentation.main.home.HomeViewModel
import com.example.e_commerce_project.presentation.main.productdetail.ProductDetailScreen
import com.example.e_commerce_project.presentation.main.productdetail.ProductDetailViewModel
import com.example.e_commerce_project.presentation.main.profile.ProfileScreen
import com.example.e_commerce_project.presentation.main.profile.ProfileViewModel
import com.example.e_commerce_project.presentation.splash.SplashScreen
import com.example.e_commerce_project.presentation.splash.SplashViewModel
import kotlinx.coroutines.flow.Flow


private val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(Home, Cart, Profile)

data class NavigationEffect(val route: Route)

@Composable
fun AppNavigation() {
    val topLevelBackStack = remember { TopLevelBackStack<Any>(Route.Splash) }

    Scaffold(
        bottomBar = {
            if (topLevelBackStack.topLevelKey in TOP_LEVEL_ROUTES) {
                NavigationBar {
                    TOP_LEVEL_ROUTES.forEach { topLevelRoute ->

                        val isSelected = topLevelRoute == topLevelBackStack.topLevelKey
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                topLevelBackStack.addTopLevel(topLevelRoute)
                            },
                            icon = {
                                Icon(
                                    imageVector =
                                        if (isSelected) {
                                            topLevelRoute.selectedIcon!!
                                        } else topLevelRoute.unselectedIcon!!,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavDisplay(
            modifier = Modifier.padding(padding),
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            entryDecorators = listOf(
                // Add the default decorators for managing scenes and saving state
                rememberSceneSetupNavEntryDecorator(),
                rememberSavedStateNavEntryDecorator(),
                // Then add the view model store decorator
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Welcome> {
                    val viewModel = hiltViewModel<WelcomeViewModel>()
                    WelcomeScreen(
                        onIntent = { viewModel.onIntent(it) }
                    )
                    NavigationHandler(viewModel.navEffect, topLevelBackStack)
                }
                entry<Route.LogIn> {
                    val viewModel = hiltViewModel<LoginViewModel>()
                    val uiState by viewModel.loginUiState.collectAsState()
                    LoginScreen(
                        onIntent = { viewModel.onIntent(it) },
                        uiState = uiState,
                    )
                    NavigationHandler(viewModel.navEffect, topLevelBackStack, true)
                }
                entry<Route.Splash> {
                    val viewModel = hiltViewModel<SplashViewModel>()
                    SplashScreen()
                    NavigationHandler(viewModel.navEffect, topLevelBackStack, true)
                }
                entry<Route.ForgotPassword> {
                    val viewModel = hiltViewModel<ForgotPasswordViewModel>()
                    val uiState by viewModel.forgotPasswordUiState.collectAsState()
                    ForgotPasswordScreen(
                        onIntent = { viewModel.onIntent(it) },
                        uiState = uiState
                    )
                    NavigationHandler(viewModel.navEffect, topLevelBackStack)
                }
                entry<Route.SignUp> {
                    val viewModel = hiltViewModel<RegisterViewModel>()
                    val uiState by viewModel.registerUiState.collectAsState()
                    RegisterScreen(
                        onIntent = { viewModel.onIntent(it) },
                        uiState = uiState
                    )
                    NavigationHandler(viewModel.navEffect, topLevelBackStack, clearStack = true)
                }
                entry<Home> {
                    val viewModel = hiltViewModel<HomeViewModel>()
                    val uiState by viewModel.uiState.collectAsState()
                    HomeScreen(
                        modifier = Modifier,
                        uiState = uiState,
                        onIntent = { viewModel.onIntent(it) }
                    )
                    NavigationHandler(viewModel.navEffect, topLevelBackStack)
                    NavigationHandler(viewModel.logOutEffect, topLevelBackStack, true)
                }
                entry<Route.ProductDetail> { key ->
                    val viewModel =
                        hiltViewModel<ProductDetailViewModel, ProductDetailViewModel.Factory>(
                            // Note: We need a new ViewModel for every new RouteB instance. Usually
                            // we would need to supply a `key` String that is unique to the
                            // instance, however, the ViewModelStoreNavEntryDecorator (supplied
                            // above) does this for us, using `NavEntry.contentKey` to uniquely
                            // identify the viewModel.
                            // tl;dr: Make sure you use rememberViewModelStoreNavEntryDecorator()
                            // if you want a new ViewModel for each new navigation key instance.
                            creationCallback = { factory ->
                                factory.create(key)
                            }
                        )
                    val uiState by viewModel.uiState.collectAsState()
                    ProductDetailScreen(
                        uiState = uiState,
                        onIntent = { viewModel.onIntent(it) }
                    )
                }
                entry<Cart> {
                    val viewModel = hiltViewModel<CartViewModel>()
                    val uiState by viewModel.uiState.collectAsState()
                    CartScreen(
                        modifier = Modifier,
                        uiState = uiState,
                        onIntent = { viewModel.onIntent(it) }
                    )
                    NavigationHandler(viewModel.navEffect, topLevelBackStack)
                }
                entry<Profile> {
                    val viewModel = hiltViewModel<ProfileViewModel>()
                    val uiState by viewModel.uiState.collectAsState()
                    ProfileScreen(
                        modifier = Modifier,
                        uiState = uiState,
                        onIntent = { viewModel.onIntent(it) }
                    )
                    NavigationHandler(viewModel.navEffect, topLevelBackStack)

                }
            }
        )
    }
}

@Composable
fun NavigationHandler(
    navigationEffect: Flow<NavigationEffect>,
    topLevelBackStack: TopLevelBackStack<Any>,
    clearStack: Boolean = false,
) {
    LaunchedEffect(Unit) {
        navigationEffect.collect {
            if (clearStack) {
                topLevelBackStack.clearStack(it.route)
                topLevelBackStack.addTopLevel(it.route)
            } else {
                Log.d("flow after", "${it.route}")
                topLevelBackStack.add(it.route)
            }
        }
    }
}
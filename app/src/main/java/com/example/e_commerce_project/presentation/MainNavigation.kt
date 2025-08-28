package com.example.e_commerce_project.presentation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.e_commerce_project.LocalNavController
import com.example.e_commerce_project.NavigationCollector
import com.example.e_commerce_project.NavigationEffect
import com.example.e_commerce_project.presentation.main.cart.CartScreen
import com.example.e_commerce_project.presentation.main.home.HomeScreen
import com.example.e_commerce_project.presentation.main.home.HomeViewModel
import com.example.e_commerce_project.presentation.main.productdetail.ProductDetailScreen
import com.example.e_commerce_project.presentation.main.productdetail.ProductDetailViewModel
import com.example.e_commerce_project.presentation.main.profile.ProfileScreen
import kotlinx.coroutines.flow.Flow


val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("NavController not provided")
}

// navController.currentBackStack.value[1].destination.route.toString()
@Composable
fun MainNavigation(parentNavController: NavHostController) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination?.route
    if (navController.currentBackStack.value.isNotEmpty()) {
        currentRoute = navController.currentBackStack.value[1].destination.route
    }
    val dontShowBottomBar = currentRoute in listOf("home", "cart", "profile")

    CompositionLocalProvider(LocalNavController provides navController) {
        Scaffold(
            bottomBar = {
                if (true) {
                    NavigationBar {
                        bottomNavigationItems.forEach { item ->
                            val isSelected = (item.title.lowercase() == currentRoute)
                            NavigationBarItem(
                                selected = isSelected,
                                label = {
                                    Text(item.title)
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                },
                                onClick = {
                                    navController.navigate(item.title.lowercase()) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    val viewModel = hiltViewModel<HomeViewModel>()
                    val uiState by viewModel.uiState.collectAsState()
                    HomeScreen(
                        modifier = Modifier,
                        uiState = uiState,
                        onIntent = { viewModel.onIntent(it) }
                    )
                    LaunchedEffect(Unit) {
                        viewModel.logOutEffect.collect {
                            parentNavController.navigate(it.route) {
                                val destRoute =
                                    navController.currentBackStack.value[1].destination.route.toString()
                                popUpTo(destRoute) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                    NavigationCollector(viewModel.navEffect)
                }
                composable("cart") {
                    CartScreen()
                }
                composable("profile") {
                    ProfileScreen()
                }
                composable("product_detail/{storeName}/{productId}") {
                    val viewModel = hiltViewModel<ProductDetailViewModel>()
                    val uiState by viewModel.uiState.collectAsState()
                    ProductDetailScreen(
                        uiState = uiState,
                        onIntent = { viewModel.onIntent(it) }
                    )
                }
//                composable(DalmarScreen.WELCOME.name) {
//                    DalmarApp()
//                }
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

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavigationItem(
        title = "Cart",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart
    ),
    BottomNavigationItem(
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    ),
)

data class BottomNavigationItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)
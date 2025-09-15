package com.example.e_commerce_project.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


sealed interface Route : NavKey {
    @Serializable
    data object Welcome : Route
    data object LogIn : Route
    data object SignUp : Route
    data object ForgotPassword : Route
    data object Splash : Route
    data object Home : Route
    data object Cart : Route
    data object Profile : Route
    data object Payment : Route
    data class ProductDetail(val storeName: String, val productId: String) : Route

}

sealed interface TopLevelRoute : Route {
    val title: String
    val unselectedIcon: ImageVector?
    val selectedIcon: ImageVector?

}

data object Welcome : TopLevelRoute {
    override val title: String
        get() = "Welcome"
    override val unselectedIcon: ImageVector?
        get() = null
    override val selectedIcon: ImageVector?
        get() = null
}

data object Home : TopLevelRoute {
    override val title: String
        get() = "Home"
    override val unselectedIcon: ImageVector
        get() = Icons.Filled.Home
    override val selectedIcon: ImageVector
        get() = Icons.Outlined.Home
}

data object Cart : TopLevelRoute {
    override val title: String
        get() = "Cart"
    override val unselectedIcon: ImageVector
        get() = Icons.Filled.ShoppingCart
    override val selectedIcon: ImageVector
        get() = Icons.Outlined.ShoppingCart
}

data object Profile : TopLevelRoute {
    override val title: String
        get() = "Profile"
    override val unselectedIcon: ImageVector
        get() = Icons.Filled.Person
    override val selectedIcon: ImageVector
        get() = Icons.Outlined.Person
}
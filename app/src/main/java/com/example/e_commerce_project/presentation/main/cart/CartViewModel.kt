package com.example.e_commerce_project.presentation.main.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromCartRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromFavoritesRequest
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
import com.example.e_commerce_project.domain.repository.UserRepository
import com.example.e_commerce_project.presentation.navigation.NavigationEffect
import com.example.e_commerce_project.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _navEffect = Channel<NavigationEffect>()
    val navEffect = _navEffect.receiveAsFlow()

    init {
        loadCartAndFavorites()
    }

    fun onIntent(intent: CartIntent) {
        when (intent) {
            is CartIntent.deleteFromCart -> deleteFromCart(intent)
            is CartIntent.deleteFromFavorites -> deleteFromFavorites(intent)
            is CartIntent.onProductClick -> navigateToProduct(intent)
            is CartIntent.RefreshCart -> loadCartAndFavorites()
            is CartIntent.Checkout -> handleCheckout()
        }
    }

    private fun loadCartAndFavorites() {
        viewModelScope.launch {
            try {
                _uiState.value = CartUiState.Loading
                val userId = userPreferencesRepository.getUserId()

                // Load cart and favorites - you'll need to implement these methods in UserRepository
                val cartProducts =
                    userRepository.getCartProducts(userId.toString()).getOrNull() ?: emptyList()
                val favoritesProducts =
                    userRepository.getFavorites(userId.toString()).getOrNull() ?: emptyList()

                if (cartProducts.isEmpty() && favoritesProducts.isEmpty()) {
                    _uiState.value = CartUiState.Empty("Your cart and favorites are empty")
                } else {
                    _uiState.value = CartUiState.Success(
                        cartProducts = cartProducts,
                        favoritesProducts = favoritesProducts
                    )
                }
            } catch (e: Exception) {
                _uiState.value = CartUiState.Error("Error loading cart: ${e.message}")
            }
        }
    }

    private fun deleteFromCart(intent: CartIntent.deleteFromCart) {
        viewModelScope.launch {
            try {
                val userId = userPreferencesRepository.getUserId()
                userRepository.deleteFromCart(
                    userId.toString(),
                    DeleteFromCartRequest(
                        userId = userPreferencesRepository.getUserId()!!,
                        id = intent.productId
                    )
                )
                // Refresh after deletion
                loadCartAndFavorites()
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error deleting from cart", e)
            }
        }
    }

    private fun deleteFromFavorites(intent: CartIntent.deleteFromFavorites) {
        viewModelScope.launch {
            try {
                val userId = userPreferencesRepository.getUserId()
                userRepository.deleteFromFavorites(
                    userId.toString(),
                    DeleteFromFavoritesRequest(
                        userId = userPreferencesRepository.getUserId()!!,
                        id = intent.productId
                    )
                )
                // Refresh after deletion
                loadCartAndFavorites()
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error deleting from favorites", e)
            }
        }
    }

    private fun navigateToProduct(intent: CartIntent.onProductClick) {
        viewModelScope.launch {
            _navEffect.send(NavigationEffect(Route.ProductDetail(intent.storeName, intent.id)))
        }
    }

    private fun handleCheckout() {
        // Implement checkout logic
        viewModelScope.launch {
            // Navigate to checkout screen or handle checkout process
            Log.d("CartViewModel", "Checkout initiated")
        }
    }
}


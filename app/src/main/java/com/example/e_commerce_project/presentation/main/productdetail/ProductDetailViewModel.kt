package com.example.e_commerce_project.presentation.main.productdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.data.remote.dto.response.AddToCartRequest
import com.example.e_commerce_project.data.remote.dto.response.AddToFavoritesRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromCartRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromFavoritesRequest
import com.example.e_commerce_project.domain.repository.StoreRepository
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
import com.example.e_commerce_project.domain.repository.UserRepository
import com.example.e_commerce_project.presentation.navigation.Route
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProductDetailViewModel.Factory::class)
class ProductDetailViewModel @AssistedInject constructor(
    private val storeRepository: StoreRepository,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    @Assisted val navKey: Route.ProductDetail
) : ViewModel() {

//    val productId = savedStateHandle.get<>("productId")!!
//    val storeName = savedStateHandle.get<String>("storeName")!!


    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()


    init {
        Log.d("flow viewmodel", "${navKey.storeName} ${navKey.productId}")
        viewModelScope.launch {
            loadProductDetail(navKey.storeName, navKey.productId.toInt())
        }
    }

    fun onIntent(intent: ProductDetailIntent) {
        when (intent) {
            is ProductDetailIntent.AddToCart -> addToCart(intent)
            is ProductDetailIntent.AddToFavorite -> addToFavorite(intent)
            is ProductDetailIntent.DeleteFromCart -> deleteFromCart(intent)
            is ProductDetailIntent.DeleteFromFavorites -> deleteFromFavorites(intent)
        }
    }

    private fun deleteFromCart(intent: ProductDetailIntent.DeleteFromCart) {
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
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error deleting from cart", e)
            }
        }
    }

    private fun deleteFromFavorites(intent: ProductDetailIntent.DeleteFromFavorites) {
        viewModelScope.launch {
            try {
                val userId = userPreferencesRepository.getUserId()
                userRepository.deleteFromFavorites(
                    userId.toString(),
                    DeleteFromFavoritesRequest(
                        userId = userPreferencesRepository.getUserId()!!,
                        id = intent.productId
                    )
                ).onSuccess {
                    _uiState.update { (it as ProductDetailUiState.Success).copy(isFavorite = false) }
                }
                // Refresh after deletion
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error deleting from favorites", e)
            }
        }
    }

    private fun addToFavorite(intent: ProductDetailIntent.AddToFavorite) {
        viewModelScope.launch {
            userRepository.addToFavorites(
                intent.store,
                AddToFavoritesRequest(userPreferencesRepository.getUserId()!!, intent.productId)
            ).onSuccess {
                _uiState.update { (it as ProductDetailUiState.Success).copy(isFavorite = true) }
            }

        }
    }

    private fun addToCart(intent: ProductDetailIntent.AddToCart) {
        viewModelScope.launch {
            userRepository.addToCart(
                intent.store,
                AddToCartRequest(userPreferencesRepository.getUserId()!!, intent.productId)
            )
        }
    }

    private suspend fun loadProductDetail(storeName: String, productId: Int) {
        val favorites =
            userRepository.getFavorites(userPreferencesRepository.getUserId()!!, storeName)
        favorites.onSuccess {
            _uiState.value = ProductDetailUiState.Success(
                product = storeRepository.getProductDetail(storeName, productId).getOrThrow(),
                isFavorite = it.any { it.id == productId },
                storeName = storeName
            )
        }
//        storeRepository.getProductDetail(storeName, productId).onSuccess {
//            _uiState.value = ProductDetailUiState.Success(product = it)
//        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: Route.ProductDetail): ProductDetailViewModel
    }
}

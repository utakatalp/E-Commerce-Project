package com.example.e_commerce_project.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.data.remote.dto.response.AddToCartRequest
import com.example.e_commerce_project.data.remote.dto.response.AddToFavoritesRequest
import com.example.e_commerce_project.data.remote.dto.response.DeleteFromFavoritesRequest
import com.example.e_commerce_project.domain.model.Store
import com.example.e_commerce_project.domain.model.User
import com.example.e_commerce_project.domain.repository.StoreRepository
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
import com.example.e_commerce_project.domain.repository.UserRepository
import com.example.e_commerce_project.presentation.navigation.NavigationEffect
import com.example.e_commerce_project.presentation.navigation.Route
import com.example.e_commerce_project.presentation.navigation.Welcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _navEffect = Channel<NavigationEffect>()
    val navEffect = _navEffect.receiveAsFlow()
    private val _logOutEffect = Channel<NavigationEffect>()
    val logOutEffect = _logOutEffect.receiveAsFlow()

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.onLogoutClick -> logOut()
            is HomeIntent.onProductClick -> navigateToProduct(intent)
            is HomeIntent.onAddToCartClick -> addToCart(intent)
            is HomeIntent.onFavoriteClick -> addToFavorite(intent)
            is HomeIntent.DeleteFromFavorites -> deleteFromFavorites(intent)
        }
    }

    init {
        viewModelScope.launch {
            Log.d("unexpected", "viewmodel ok")
            val deferredStore = async { loadStore("canerture") }
            val deferredUser = async { loadUser() }
            Log.d("deferred store", deferredStore.await().toString())
            _uiState.value = HomeUiState.Success(
                stores = listOf(deferredStore.await()),
                user = deferredUser.await(),
            )
        }
    }

    private fun deleteFromFavorites(intent: HomeIntent.DeleteFromFavorites) {
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
                    _uiState.update { (it as HomeUiState.Success).copy(stores = listOf(loadStore("canerture"))) }
                }
                // Refresh after deletion
            } catch (e: Exception) {
                Log.e("CartViewModel", "Error deleting from favorites", e)
            }
        }
    }

    private fun addToCart(intent: HomeIntent.onAddToCartClick) {
        viewModelScope.launch {
            try {
                val userId = userPreferencesRepository.getUserId()
                userRepository.addToCart(
                    intent.storeName,
                    AddToCartRequest(
                        productId = intent.productId,
                        userId = userPreferencesRepository.getUserId()!!
                    )
                )
                // You might want to show a success message or update UI state
                Log.d("HomeViewModel", "Product added to cart successfully")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error adding to cart", e)
            }
        }
    }

    private fun addToFavorite(intent: HomeIntent.onFavoriteClick) {
        viewModelScope.launch {
            try {
                val userId = userPreferencesRepository.getUserId()
                userRepository.addToFavorites(
                    intent.storeName,
                    AddToFavoritesRequest(
                        userId = userPreferencesRepository.getUserId()!!,
                        productId = intent.productId
                    )
                ).onSuccess {
                    _uiState.update { (it as HomeUiState.Success).copy(stores = listOf(loadStore("canerture"))) }
                }
                // You might want to update the UI state to reflect the favorite status
                Log.d("HomeViewModel", "Product added to favorites successfully")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error adding to favorites", e)
            }
        }
    }


    private fun navigateToProduct(intent: HomeIntent.onProductClick) {
        viewModelScope.launch {
            //                _navEffect.send(NavigationEffect("product_detail/${intent.storeName}/${intent.id}"))
            Log.d("flow before", "${intent.id}, ${intent.storeName}")
            _navEffect.send(NavigationEffect(Route.ProductDetail(intent.storeName, intent.id)))
        }
    }



    private suspend fun loadStore(storeName: String): Store {
        return storeRepository.getStore(storeName).getOrThrow()
    }
    private suspend fun loadUser(): User {
        val userId = userPreferencesRepository.getUserId()
        Log.d("unexpected", userId.toString())
        return userRepository.getUser(userId.toString()).getOrThrow()
    }

//        viewModelScope.launch {
//            val userId = userPreferencesRepository.getUserId()
//            val userResponse = userRepository.getUser(userId.toString())
//
//            storeRepository.getStore(store).onSuccess { store ->
//                _uiState.value = HomeUiState.Success(
//                    stores = listOf(store),
//                    user = userResponse.getOrNull()!!,
//                    categories = emptyList()
//                )
//            }.onFailure {
//                Log.d("HomeViewModel", it.message.toString())
//            }
//        }
//    }

    private fun logOut() {
        viewModelScope.launch {
            if (userRepository.logOut().isSuccess) {
//                _navEffect.send(NavigationEffect(DalmarScreen.WELCOME.name))
//                _logOutEffect.send(NavigationEffect(DalmarScreen.WELCOME.name))
                _logOutEffect.send(NavigationEffect(Welcome))
            }
        }
    }

    private fun logOutEffect() {
        viewModelScope.launch {
            val _logOutEffect = _navEffect
        }
    }
}
package com.example.e_commerce_project.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.DalmarScreen
import com.example.e_commerce_project.NavigationEffect
import com.example.e_commerce_project.domain.model.Store
import com.example.e_commerce_project.domain.model.User
import com.example.e_commerce_project.domain.repository.StoreRepository
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
import com.example.e_commerce_project.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
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

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.onLogoutClick -> logOut()
            is HomeIntent.onProductClick -> TODO()
        }
    }

    init {

        viewModelScope.launch {
            val deferredStore = async { loadStore("canerture") }
            val deferredUser = async { loadUser() }

            _uiState.value = HomeUiState.Success(
                stores = listOf(deferredStore.await()),
                user = deferredUser.await(),
                categories = emptyList()
            )
        }
//        loadProducts("canerture")
//        loadProducts("alpsahin")
    }
    private suspend fun loadStore(store: String): Store {
        return storeRepository.getStore(store).getOrThrow()
    }
    private suspend fun loadUser(): User {
        val userId = userPreferencesRepository.getUserId()
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
                _navEffect.send(NavigationEffect(DalmarScreen.WELCOME.name))
            }
        }
    }
}
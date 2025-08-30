package com.example.e_commerce_project.presentation.main.productdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.domain.repository.StoreRepository
import com.example.e_commerce_project.presentation.navigation.Route
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProductDetailViewModel.Factory::class)
class ProductDetailViewModel @AssistedInject constructor(
    private val storeRepository: StoreRepository,
    savedStateHandle: SavedStateHandle,
    @Assisted val navKey: Route.ProductDetail
) : ViewModel() {

//    val productId = savedStateHandle.get<>("productId")!!
//    val storeName = savedStateHandle.get<String>("storeName")!!


    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()


    init {
        Log.d("check", "$navKey.storeName $navKey.productId")
        viewModelScope.launch {
            loadProductDetail(navKey.storeName, navKey.productId.toInt())
        }
    }

    fun onIntent(intent: ProductDetailIntent) {
        when (intent) {

        }
    }

    private suspend fun loadProductDetail(storeName: String, productId: Int) {
        storeRepository.getProductDetail(storeName, productId).onSuccess {
            _uiState.value = ProductDetailUiState.Success(product = it)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: Route.ProductDetail): ProductDetailViewModel
    }
}

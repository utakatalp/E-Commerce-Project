package com.example.e_commerce_project.presentation.main.productdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.domain.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val storeRepository: StoreRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val productId = savedStateHandle.get<String>("productId")!!
    val storeName = savedStateHandle.get<String>("storeName")!!


    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()


    init {
        Log.d("check", "$storeName $productId")
        viewModelScope.launch {
            loadProductDetail(storeName, productId.toInt())
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
}

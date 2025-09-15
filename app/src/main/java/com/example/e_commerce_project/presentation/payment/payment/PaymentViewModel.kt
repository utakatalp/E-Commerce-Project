package com.example.e_commerce_project.presentation.payment.payment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.domain.model.AddressWithCheck
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
import com.example.e_commerce_project.domain.repository.UserRepository
import com.example.e_commerce_project.presentation.navigation.NavigationEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<PaymentUiState>(PaymentUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _navEffect = Channel<NavigationEffect>()
    val navEffect = _navEffect.receiveAsFlow()

    fun onIntent(intent: PaymentIntent) {
        when (intent) {
            is PaymentIntent.OnPaymentClick -> TODO()
            is PaymentIntent.SelectAddress -> {
                (_uiState as PaymentUiState.Success).addresses[intent.index].isChecked =
                    !(_uiState as PaymentUiState.Success).addresses[intent.index].isChecked
            }
        }
    }

    init {
        loadAddresses()
    }

    fun loadAddresses() {
        viewModelScope.launch {
            val userId = userPreferencesRepository.getUserId()
            userRepository.getAddresses(userId = userId!!)
                .onSuccess {
                    Log.d("PaymentViewModel", "loadAddresses: $it")
                    val addressesWithCheck = it.map { address ->
                        AddressWithCheck(address = address)
                    }
                    _uiState.update {
                        PaymentUiState.Success(
                            addresses = addressesWithCheck
                        )
                    }
                }
        }
    }
}
package com.example.e_commerce_project.presentation.auth.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.DalmarScreen
import com.example.e_commerce_project.NavigationEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {

    val _navEffect = Channel<NavigationEffect>()
    val navEffect = _navEffect.receiveAsFlow()

    fun onIntent(intent: WelcomeIntent) {
        when (intent) {
            WelcomeIntent.LoginButtonClick -> { navigateToLogin() }

            WelcomeIntent.RegisterButtonClick -> { navigateToRegister() }
        }

    }
    private fun navigateToRegister() {
        viewModelScope.launch {
            _navEffect.send(NavigationEffect(DalmarScreen.REGISTER.name))
        }
    }
    private fun navigateToLogin() {
        viewModelScope.launch {
            _navEffect.send(NavigationEffect(DalmarScreen.LOGIN.name))
        }
    }
}
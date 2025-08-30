package com.example.e_commerce_project.presentation.auth.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.presentation.navigation.NavigationEffect
import com.example.e_commerce_project.presentation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


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
            _navEffect.send(NavigationEffect(Route.SignUp))
        }
    }
    private fun navigateToLogin() {
        viewModelScope.launch {
//            _navEffect.send(NavigationEffect2(Dalmar_root_ide_package_.com.example.e_commerce_project.presentation.navigation.Route.LOGIN.name))
            _navEffect.send(NavigationEffect(Route.LogIn))
        }
    }
}
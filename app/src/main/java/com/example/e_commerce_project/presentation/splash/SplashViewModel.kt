package com.example.e_commerce_project.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
import com.example.e_commerce_project.presentation.navigation.Home
import com.example.e_commerce_project.presentation.navigation.NavigationEffect
import com.example.e_commerce_project.presentation.navigation.Welcome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private val _navEffect = Channel<NavigationEffect>()
    val navEffect = _navEffect.receiveAsFlow()

    init {
        viewModelScope.launch {
            if (userPreferencesRepository.getUserId() != null) {
                _navEffect.send(NavigationEffect(Home))
            } else {
                _navEffect.send(NavigationEffect(Welcome))
            }
        }
    }
}
package com.example.e_commerce_project.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerce_project.DalmarScreen
import com.example.e_commerce_project.NavigationEffect
import com.example.e_commerce_project.domain.repository.UserPreferencesRepository
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
                _navEffect.send(NavigationEffect(DalmarScreen.HOME.name))
            } else {
                _navEffect.send(NavigationEffect(DalmarScreen.WELCOME.name))
            }
        }
    }
}
package com.example.e_commerce_project.presentation.auth.welcome

sealed interface WelcomeIntent {
    object RegisterButtonClick : WelcomeIntent
    object LoginButtonClick: WelcomeIntent
}
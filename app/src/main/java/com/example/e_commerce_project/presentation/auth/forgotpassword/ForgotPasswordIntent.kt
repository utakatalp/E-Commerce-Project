package com.example.e_commerce_project.presentation.auth.forgotpassword

sealed interface ForgotPasswordIntent {
    data class EnterEmail(val email: String): ForgotPasswordIntent
    object SubmitForgotPassword: ForgotPasswordIntent
    object NavigatoToLogin: ForgotPasswordIntent
}
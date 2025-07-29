package com.example.e_commerce_project.ui.forgotpassword

sealed class ForgotPasswordIntent {
    data class EnterEmail(val email: String): ForgotPasswordIntent()
    object submitForgotPassword: ForgotPasswordIntent()
}
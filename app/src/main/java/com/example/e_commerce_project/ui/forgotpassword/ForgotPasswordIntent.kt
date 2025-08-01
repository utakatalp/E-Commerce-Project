package com.example.e_commerce_project.ui.forgotpassword

sealed interface ForgotPasswordIntent {
    data class EnterEmail(val email: String): ForgotPasswordIntent
    object SubmitForgotPassword: ForgotPasswordIntent
}
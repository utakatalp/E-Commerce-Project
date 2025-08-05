package com.example.e_commerce_project.ui.register


sealed interface RegisterIntent {
    data class EnterName(val name: String) : RegisterIntent
    data class EnterEmail(val email: String) : RegisterIntent
    object SubmitRegister : RegisterIntent
    data class EnterPassword(val password: String) : RegisterIntent
    data class EnterPhone(val phone: String) : RegisterIntent
    object ShowPassword : RegisterIntent
}

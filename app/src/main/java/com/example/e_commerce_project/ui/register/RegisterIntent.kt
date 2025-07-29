package com.example.e_commerce_project.ui.register


sealed class RegisterIntent {
    data class EnterName(val name: String) : RegisterIntent()
    data class EnterSurname(val surname: String) : RegisterIntent()
    data class EnterEmail(val email: String) : RegisterIntent()
    data class EnterPassword(val password: String) : RegisterIntent()
    object SubmitRegister : RegisterIntent()
}
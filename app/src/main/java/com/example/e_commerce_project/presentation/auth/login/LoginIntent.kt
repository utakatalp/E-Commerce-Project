package com.example.e_commerce_project.presentation.auth.login


// https://www.youtube.com/watch?v=0J0WGlbyVFA burada interface kullanılmış ama ben mantıklı bulmadım, sebebini sor
sealed interface LoginIntent {
    data class EnterEmail(val email: String) : LoginIntent
    data class EnterPassword(val password: String) : LoginIntent
    object ShowPassword : LoginIntent
    object SubmitLogin : LoginIntent
    object ClickForgotPassword : LoginIntent
}

sealed interface Deneme {
    val a: String

    data class action1(override val a: String) : Deneme
    data class action2(override val a: String) : Deneme
}




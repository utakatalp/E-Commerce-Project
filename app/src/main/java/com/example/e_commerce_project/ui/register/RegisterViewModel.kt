package com.example.e_commerce_project.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.e_commerce_project.util.password.MinLengthRule
import com.example.e_commerce_project.util.password.PasswordValidator
import com.example.e_commerce_project.util.password.UppercaseRule
import com.example.e_commerce_project.util.password.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern

class RegisterViewModel : ViewModel() {

    // private val _loginUiState = MutableStateFlow(LoginUiState())
    // val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    val rules = listOf(
        MinLengthRule(8),
        UppercaseRule()
    )
    val validator = PasswordValidator(rules)


    fun onIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.EnterEmail -> {
                onEmailChange(intent)
            }

            is RegisterIntent.EnterName -> {
                _registerUiState.update {
                    it.copy(name = intent.name)
                }
            }

            is RegisterIntent.EnterPassword -> {
                _registerUiState.update {
                    it.copy(password = intent.password)
                }
            }

            is RegisterIntent.EnterSurname -> {
                _registerUiState.update {
                    it.copy(surname = intent.surname)
                }
            }

            is RegisterIntent.SubmitRegister -> {
                val passwordWarnings = validator.validate(registerUiState.value.password)
                val warnings = mutableListOf<ValidationResult>()
                warnings += passwordWarnings

                if (!isValidEmail(registerUiState.value.email)) {
                    _registerUiState.update { it.copy(warning = it.warning + ValidationResult(false, "Invalid Email")) }
                    warnings += ValidationResult(false, "Your email is invalid.")
                }
                /*
                if (warnings.isNotEmpty()) {
                    _registerUiState.update {
                        it.copy(warning = warnings)
                    }
                } else {
                    _registerUiState.update {
                        it.copy(warning = emptyList())
                    }

                }
                */
                _registerUiState.update {
                    it.copy(warning = warnings)
                }
            }
        }
    }

    private fun onEmailChange(intent: RegisterIntent.EnterEmail) {
        _registerUiState.update {
            it.copy(email = intent.email)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        //Patterns.EMAIL_ADDRESS.matcher(email).matches()

        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"

        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
    /*
    private fun isValidPassword(password: String): Boolean {
        if(password.length < 8) return false
        if(password.star)
    }
    */

}
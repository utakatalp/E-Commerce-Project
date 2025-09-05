package com.example.e_commerce_project.presentation.auth.register

//import com.example.e_commerce_project.util.api.RetrofitInstance
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce_project.data.remote.dto.response.RegisterRequest
import com.example.e_commerce_project.data.repository.ExistingUser
import com.example.e_commerce_project.domain.repository.UserRepository
import com.example.e_commerce_project.presentation.navigation.Home
import com.example.e_commerce_project.presentation.navigation.NavigationEffect
import com.example.e_commerce_project.util.password.MinLengthRule
import com.example.e_commerce_project.util.password.PasswordValidator
import com.example.e_commerce_project.util.password.UppercaseRule
import com.example.e_commerce_project.util.password.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    // private val _loginUiState = MutableStateFlow(LoginUiState())
    // val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    private val _registerUiState = MutableStateFlow(RegisterUiState())
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    private val _navEffect = Channel<NavigationEffect>()
    val navEffect = _navEffect.receiveAsFlow()

    val rules = listOf(
        MinLengthRule(8),
        UppercaseRule()
    )
    val validator = PasswordValidator(rules)


    fun onIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.EnterEmail -> onEmailChange(intent)
            is RegisterIntent.EnterName -> onNameChange(intent)
            is RegisterIntent.EnterPassword -> onPasswordChange(intent)
            is RegisterIntent.EnterPhone -> onPhoneChange(intent)
            is RegisterIntent.SubmitRegister -> onSubmit()
            is RegisterIntent.ShowPassword -> { _registerUiState.update { it.copy(showPassword = !registerUiState.value.showPassword) }}
        }
    }

    private fun onPhoneChange(intent: RegisterIntent.EnterPhone) {
        _registerUiState.update {
            it.copy(phone = intent.phone)
        }
    }

    private fun onSubmit() {
        val passwordWarnings = validator.validate(registerUiState.value.password)
        val warnings = mutableListOf<ValidationResult>()
        warnings += passwordWarnings

        if (!isValidEmail(registerUiState.value.email)) {
            warnings += ValidationResult(false, "Your email is invalid.")
        }
        _registerUiState.update {
            it.copy(
                warning = (warnings)
            )
        }

        if (warnings.isEmpty()) {
            val registerRequest = RegisterRequest(
                email = registerUiState.value.email,
                password = registerUiState.value.password,
                name = registerUiState.value.name,
                phone = registerUiState.value.phone,
                address = "teknasyon"
            )
            performAuthWithExtension(registerRequest) { result ->
                if (result != null) {
                    _registerUiState.update {
                        it.copy(
                            warning = (warnings + ValidationResult(
                                false,
                                result
                            ))
                        )
                    }
                }
            }
        }
    }


    private fun onPasswordChange(intent: RegisterIntent.EnterPassword) {
        _registerUiState.update {
            it.copy(password = intent.password)
        }
    }

    private fun onNameChange(intent: RegisterIntent.EnterName) {
        _registerUiState.update {
            it.copy(name = intent.name)
        }
    }

    private fun onEmailChange(intent: RegisterIntent.EnterEmail) {
        _registerUiState.update {
            it.copy(email = intent.email)
        }
    }

    private fun performAuthWithExtension(
        registerRequest: RegisterRequest,
        onResult: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = userRepository.signUp(registerRequest)
                Log.d("response", response.toString())
                response
                    .onSuccess {
                        Log.d("response", it.toString())
                        _navEffect.send(NavigationEffect(Home))
                        onResult(null)
                    }
                    .onFailure {
                        if (it is ExistingUser) {
                            onResult("The email is already used.")
                        }
                    }

            } catch (e: Exception) {
                _registerUiState.update {
                    it.copy(
                        errorMessage = e,
                    )
                }
                onResult("An exception occurred.")
            }
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
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ECommerceApplication)
                val userRepository = application.container.userRepository
                RegisterViewModel(userRepository)
            }
        }
    }

     */
}
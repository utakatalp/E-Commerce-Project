package com.example.e_commerce_project.util.password

class MinLengthRule(private val minLength: Int): PasswordRule{
    override fun validate(password: String): ValidationResult {
        return if(password.length >= minLength) {
            ValidationResult(true)
        } else{
            ValidationResult(false, "Your password must have $minLength characters at least.")
        }
    }
}

class UppercaseRule : PasswordRule {
    override fun validate(password: String): ValidationResult {
        return if(password.any { it.isUpperCase()}) {
            ValidationResult(true)
        } else{
            ValidationResult(false, "Your password must have at least one capital letter.")
        }
    }
}

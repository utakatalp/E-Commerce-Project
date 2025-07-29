package com.example.e_commerce_project.util.password

class PasswordValidator(private val rules: List<PasswordRule>) {
    fun validate(password: String): List<ValidationResult> {
        return rules.map { it.validate(password) }.filter { !it.isValid }
    }

    fun isValid(password: String): Boolean {
        return validate(password).isEmpty()
    }
}
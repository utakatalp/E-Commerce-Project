package com.example.e_commerce_project.util.password

interface PasswordRule {
    fun validate(password: String): ValidationResult
}
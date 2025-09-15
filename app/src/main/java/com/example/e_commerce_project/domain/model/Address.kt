package com.example.e_commerce_project.domain.model

data class Address(
    val id: Int,
    val title: String,
    val address: String,
)

data class AddressWithCheck(
    val address: Address,
    var isChecked: Boolean = false,
)
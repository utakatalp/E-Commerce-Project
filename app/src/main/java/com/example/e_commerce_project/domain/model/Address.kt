package com.example.e_commerce_project.domain.model

data class Address(
    val id: Int,
    val title: String,
    val address: String,
)

data class AddressList(
    val addresses: List<Address>
)
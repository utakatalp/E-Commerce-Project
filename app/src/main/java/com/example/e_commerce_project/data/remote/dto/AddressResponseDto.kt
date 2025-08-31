package com.example.e_commerce_project.data.remote.dto

import com.example.e_commerce_project.domain.model.Address

data class AddressResponseDto(
    val addresses: List<AddressDto>,
    val status: Int,
    val message: String
)

data class AddressDto(
    val id: Int,
    val title: String,
    val address: String,
) {
    fun toDomain(): Address {
        return Address(
            id = id,
            title = title,
            address = address
        )
    }
}

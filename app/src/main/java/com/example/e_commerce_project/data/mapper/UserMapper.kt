package com.example.e_commerce_project.data.mapper

import com.example.e_commerce_project.data.remote.dto.UserDto
import com.example.e_commerce_project.domain.model.User

fun UserDto.toDomain(): User =
    User(
        id = userId,
        email = email,
        name = name,
        phone = phone
    )

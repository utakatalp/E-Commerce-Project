package com.example.e_commerce_project.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: String? = null,
    val email: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val address: String? = null
)
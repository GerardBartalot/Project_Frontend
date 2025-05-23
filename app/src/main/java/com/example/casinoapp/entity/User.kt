package com.example.casinoapp.entity

data class User(
    val userId: Int,
    val name: String,
    val username: String,
    val password: String,
    val dateOfBirth: String,
    val fondocoins: Int,
    val experiencePoints: Int,
    val profilePicture: String?,
    val registrationDate: String? = null
)
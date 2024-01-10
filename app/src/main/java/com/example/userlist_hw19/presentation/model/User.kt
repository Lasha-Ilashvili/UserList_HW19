package com.example.userlist_hw19.presentation.model

data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    var isSelected: Boolean = false
)

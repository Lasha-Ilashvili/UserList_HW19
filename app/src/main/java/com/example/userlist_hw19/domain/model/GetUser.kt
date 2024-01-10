package com.example.userlist_hw19.domain.model

data class GetUser(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String
)
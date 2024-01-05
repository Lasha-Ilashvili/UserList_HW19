package com.example.userlist_hw19.data.user_page.mapper

import com.example.userlist_hw19.data.user_page.model.UserDtoResponse
import com.example.userlist_hw19.domain.model.User

fun UserDtoResponse.toDomain() =
    User(data.id, data.email, data.firstName, data.lastName, data.avatar)
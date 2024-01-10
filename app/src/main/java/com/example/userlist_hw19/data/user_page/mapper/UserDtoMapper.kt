package com.example.userlist_hw19.data.user_page.mapper

import com.example.userlist_hw19.data.user_page.model.UserDtoResponse
import com.example.userlist_hw19.domain.model.GetUser

fun UserDtoResponse.toDomain() =
    GetUser(data.id, data.email, data.firstName, data.lastName, data.avatar)
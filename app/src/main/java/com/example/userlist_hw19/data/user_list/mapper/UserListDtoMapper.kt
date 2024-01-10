package com.example.userlist_hw19.data.user_list.mapper

import com.example.userlist_hw19.data.model.UserDto
import com.example.userlist_hw19.domain.model.GetUser

fun List<UserDto>.toDomain() = this.map {
    GetUser(
        id = it.id,
        email = it.email,
        firstName = it.firstName,
        lastName = it.lastName,
        avatar = it.avatar
    )
}
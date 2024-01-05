package com.example.userlist_hw19.data.user_page.model

import com.example.userlist_hw19.data.model.UserDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDtoResponse(
    val data: UserDto
)

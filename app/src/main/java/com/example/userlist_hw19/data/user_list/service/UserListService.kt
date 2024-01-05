package com.example.userlist_hw19.data.user_list.service

import com.example.userlist_hw19.data.model.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface UserListService {
    @GET("7ec14eae-06bf-4f6d-86d2-ac1b9df5fe3d")
    suspend fun getUserList(): Response<List<UserDto>>
}
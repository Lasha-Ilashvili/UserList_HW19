package com.example.userlist_hw19.data.user_page.service

import com.example.userlist_hw19.data.user_page.model.UserDtoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserPageService {
    @GET("users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<UserDtoResponse>
}
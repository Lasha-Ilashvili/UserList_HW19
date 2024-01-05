package com.example.userlist_hw19.domain.user_page

import com.example.userlist_hw19.data.user_page.resources.UserPageState
import com.example.userlist_hw19.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserPageRepository {
    suspend fun getUser(id: Int): Flow<UserPageState<User>>
}
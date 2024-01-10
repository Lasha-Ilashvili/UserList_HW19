package com.example.userlist_hw19.domain.user_list

import com.example.userlist_hw19.data.user_list.resources.UserListState
import com.example.userlist_hw19.domain.model.GetUser
import kotlinx.coroutines.flow.Flow

interface UserListRepository {
    suspend fun getUserList(): Flow<UserListState<List<GetUser>>>
}
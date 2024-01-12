package com.example.userlist_hw19.domain.use_case

import com.example.userlist_hw19.data.user_list.resources.UserListResult
import com.example.userlist_hw19.domain.model.GetUser
import com.example.userlist_hw19.domain.user_list.UserListRepository
import kotlinx.coroutines.flow.Flow

class GetUserListState(private val userListRepository: UserListRepository) {

    suspend operator fun invoke(): Flow<UserListResult<List<GetUser>>> {
        /*Use mapper*/
        return userListRepository.getUserList()
    }
}
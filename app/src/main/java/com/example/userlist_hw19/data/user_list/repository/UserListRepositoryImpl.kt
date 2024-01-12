package com.example.userlist_hw19.data.user_list.repository

import com.example.userlist_hw19.data.user_list.mapper.toDomain
import com.example.userlist_hw19.data.user_list.resources.UserListResult
import com.example.userlist_hw19.data.user_list.service.UserListService
import com.example.userlist_hw19.domain.model.GetUser
import com.example.userlist_hw19.domain.user_list.UserListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserListRepositoryImpl @Inject constructor(private val userListService: UserListService) :
    UserListRepository {

    override suspend fun getUserList(): Flow<UserListResult<List<GetUser>>> {
        return flow {
            emit(UserListResult.Loading(isLoading = true))
            try {
                val response = userListService.getUserList()
                if (response.isSuccessful) {
                    emit(UserListResult.Success(response.body()!!.toDomain()))
                } else {
                    emit(UserListResult.Error("Fetching user list failed"))
                }
            } catch (e: Exception) {
                emit(UserListResult.Error("Network error"))
            } finally {
                emit(UserListResult.Loading(isLoading = false))
            }
        }
    }
}
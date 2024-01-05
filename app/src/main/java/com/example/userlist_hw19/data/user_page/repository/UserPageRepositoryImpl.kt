package com.example.userlist_hw19.data.user_page.repository

import com.example.userlist_hw19.data.user_page.mapper.toDomain
import com.example.userlist_hw19.data.user_page.resources.UserPageState
import com.example.userlist_hw19.data.user_page.service.UserPageService
import com.example.userlist_hw19.domain.model.User
import com.example.userlist_hw19.domain.user_page.UserPageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserPageRepositoryImpl @Inject constructor(private val userPageService: UserPageService) :
    UserPageRepository {

    override suspend fun getUser(id: Int): Flow<UserPageState<User>> {
        return flow {
            emit(UserPageState.Loading(isLoading = true))
            try {
                val response = userPageService.getUser(id)
                if (response.isSuccessful) {
                    emit(UserPageState.Success(response.body()!!.toDomain()))
                } else {
                    emit(UserPageState.Error("Fetching user failed"))
                }
//            } catch (e: Exception) {
//                emit(UserPageState.Error("Network error"))
            } finally {
                emit(UserPageState.Loading(isLoading = false))
            }
        }
    }
}
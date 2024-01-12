package com.example.userlist_hw19.data.user_list.resources

sealed class UserListResult<T> {
    data class Success<T>(val userList: T) : UserListResult<T>()
    data class Error<T>(val error: String) : UserListResult<T>()
    data class Loading<T>(val isLoading: Boolean) : UserListResult<T>()
}
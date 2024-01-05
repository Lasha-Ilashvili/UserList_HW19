package com.example.userlist_hw19.data.user_list.resources

sealed class UserListState<T> {
    data class Success<T>(val userList: T) : UserListState<T>()
    data class Error<T>(val error: String) : UserListState<T>()
    data class Loading<T>(val isLoading: Boolean) : UserListState<T>()
    data class OnClick<T>(val id: Int) : UserListState<T>()
}
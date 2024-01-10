package com.example.userlist_hw19.data.user_list.resources

sealed class UserListState<T> {
    data class Success<T>(val userList: T) : UserListState<T>()
    data class Error<T>(val error: String) : UserListState<T>()
    data class Loading<T>(val isLoading: Boolean) : UserListState<T>()
    data class OnClick<T>(val position: Int, val count: Int ) : UserListState<T>()
    data class OnLongClick<T>(val position: Int, val count: Int) : UserListState<T>()
    class ItemRemoved<T>(val value: T) : UserListState<T>()
}
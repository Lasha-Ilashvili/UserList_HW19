package com.example.userlist_hw19.data.user_page.resources

import com.example.userlist_hw19.domain.model.User

sealed class UserPageState<T> {
    data class Success<T>(val user: User) : UserPageState<T>()
    data class Error<T>(val error: String) : UserPageState<T>()
    data class Loading<T>(val isLoading: Boolean) : UserPageState<T>()
    data class OnClick<T>(val isClicked: Boolean) : UserPageState<T>()
}
package com.example.userlist_hw19.presentation.user_list.event

sealed class UserListEvent {
    data object SetUpUserList : UserListEvent()
    data class SendClickEvent(val id: Int) : UserListEvent()
}
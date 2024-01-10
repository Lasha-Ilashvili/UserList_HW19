package com.example.userlist_hw19.presentation.user_list.event

sealed class UserListEvent {
    data object SetUpUserListEvent : UserListEvent()
    data class SendClickEvent(val position: Int, val isSelected: Boolean) : UserListEvent()
    data class SendLongClickEvent(val position: Int, val isSelected: Boolean) : UserListEvent()
    data object RemoveUserEvent : UserListEvent()
}
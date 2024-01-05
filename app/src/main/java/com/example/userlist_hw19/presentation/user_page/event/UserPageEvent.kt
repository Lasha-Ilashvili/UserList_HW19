package com.example.userlist_hw19.presentation.user_page.event

sealed class UserPageEvent {
    data class SetUpUserPage(val id: Int) : UserPageEvent()
    data class SendClickEvent(val isClicked: Boolean) : UserPageEvent()
}
package com.example.userlist_hw19.presentation.user_page.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist_hw19.data.user_page.resources.UserPageState
import com.example.userlist_hw19.domain.model.GetUser
import com.example.userlist_hw19.domain.user_page.UserPageRepository
import com.example.userlist_hw19.presentation.user_page.event.UserPageEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPageViewModel @Inject constructor(private val userPageRepository: UserPageRepository) :
    ViewModel() {

    private val _userPageState =
        MutableStateFlow<UserPageState<GetUser>>(UserPageState.Loading(isLoading = true))

    val userPageState get() = _userPageState.asStateFlow()

    fun onEvent(event: UserPageEvent) {
        when (event) {
            is UserPageEvent.SetUpUserPage -> getUser(event.id)

            is UserPageEvent.SendClickEvent -> getClickState(event.isClicked)
        }
    }

    private fun getUser(id: Int) {
        viewModelScope.launch {
            userPageRepository.getUser(id).collect {
                _userPageState.value = it
            }
        }
    }

    private fun getClickState(isClicked: Boolean) {
        viewModelScope.launch {
            _userPageState.value = UserPageState.OnClick(isClicked = isClicked)
        }
    }
}
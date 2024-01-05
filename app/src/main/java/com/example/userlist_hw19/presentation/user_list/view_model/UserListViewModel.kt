package com.example.userlist_hw19.presentation.user_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist_hw19.data.user_list.resources.UserListState
import com.example.userlist_hw19.domain.model.User
import com.example.userlist_hw19.domain.user_list.UserListRepository
import com.example.userlist_hw19.presentation.user_list.event.UserListEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val userListRepository: UserListRepository) :
    ViewModel() {

    private val _userListState =
        MutableStateFlow<UserListState<List<User>>>(UserListState.Success(emptyList()))

    val userListState get() = _userListState.asStateFlow()

    fun onEvent(event: UserListEvent) {
        when (event) {
            is UserListEvent.SetUpUserList -> getUserList()

            is UserListEvent.SendClickEvent -> getClickState(event.id)
        }
    }

    private fun getUserList() {
        viewModelScope.launch {
            userListRepository.getUserList().collect {
                _userListState.value = it
            }
        }
    }

    private fun getClickState(id: Int) {
        viewModelScope.launch {
            _userListState.value = UserListState.OnClick(id = id)
        }
    }
}
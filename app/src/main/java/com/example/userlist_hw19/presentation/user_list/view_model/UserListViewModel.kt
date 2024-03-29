package com.example.userlist_hw19.presentation.user_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist_hw19.data.user_list.resources.UserListResult
import com.example.userlist_hw19.domain.user_list.UserListRepository
import com.example.userlist_hw19.presentation.mapper.toUI
import com.example.userlist_hw19.presentation.model.User
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
        MutableStateFlow<UserListResult<List<User>>>(UserListResult.Success(emptyList()))

    val userListState get() = _userListState.asStateFlow()

    private val _userList = MutableStateFlow<List<User>>(emptyList())

    private var selectedItems = 0

    init {
        viewModelScope.launch {
            userListRepository.getUserList().collect {
                if (it is UserListResult.Success) {
                    _userList.value = it.userList.map { getUser ->
                        getUser.toUI()
                    }
                }
            }
            setUserListState()
        }
    }

    fun onEvent(event: UserListEvent) {
        when (event) {
            is UserListEvent.SetUpUserListEvent -> setUserListState()

            is UserListEvent.SendClickEvent -> setClickState(event)

            is UserListEvent.SendLongClickEvent -> setSelectedState(event)

            is UserListEvent.RemoveUserEvent -> setNewUserListState()
        }
    }

    private fun setUserListState() {
        viewModelScope.launch {
            userListRepository.getUserList().collect {
                when (it) {
                    is UserListResult.Success -> _userListState.value =
                        UserListResult.Success(_userList.value)

                    is UserListResult.Error -> _userListState.value =
                        UserListResult.Error(it.error)

                    is UserListResult.Loading -> _userListState.value =
                        UserListResult.Loading(it.isLoading)

                    else -> {

                    }
                }
            }
        }
    }

    private fun setClickState(event: UserListEvent.SendClickEvent) {
        viewModelScope.launch {
            val count: Int
            _userList.value[event.position].isSelected = if (event.isSelected) {
                count = selectedItems
                false
            } else {
                count = ++selectedItems
                true
            }
            _userListState.value = UserListResult.OnClick(event.position, count)
        }
    }

    private fun setSelectedState(event: UserListEvent.SendLongClickEvent) {
        viewModelScope.launch {
            _userList.value[event.position].isSelected = if (event.isSelected) {
                selectedItems--
                false
            } else {
                selectedItems++
                true
            }
            _userListState.value = UserListResult.OnLongClick(event.position, selectedItems)
        }
    }

    private fun setNewUserListState() {
        viewModelScope.launch {
            _userList.value = _userList.value.filter {
                !it.isSelected
            }
            selectedItems = 0
            _userListState.value = UserListResult.ItemRemoved(_userList.value)
        }
    }
}

sealed class UserListState<T>() {
    data class OnClick<T>(val position: Int, val count: Int) : UserListState<T>()
    data class OnLongClick<T>(val position: Int, val count: Int) : UserListState<T>()
    data class ItemRemoved<T>(val value: T) : UserListState<T>()
}
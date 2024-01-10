package com.example.userlist_hw19.presentation.mapper

import com.example.userlist_hw19.domain.model.GetUser
import com.example.userlist_hw19.presentation.model.User

fun GetUser.toUI() = User(id, email, firstName, lastName, avatar)
package com.example.userlist_hw19.di

import com.example.userlist_hw19.data.user_list.repository.UserListRepositoryImpl
import com.example.userlist_hw19.data.user_list.service.UserListService
import com.example.userlist_hw19.data.user_page.repository.UserPageRepositoryImpl
import com.example.userlist_hw19.data.user_page.service.UserPageService
import com.example.userlist_hw19.domain.user_list.UserListRepository
import com.example.userlist_hw19.domain.user_page.UserPageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserListRepository(userListService: UserListService): UserListRepository =
        UserListRepositoryImpl(userListService)

    @Singleton
    @Provides
    fun provideUserPageRepository(userPageService: UserPageService): UserPageRepository =
        UserPageRepositoryImpl(userPageService)
}
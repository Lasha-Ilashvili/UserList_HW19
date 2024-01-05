package com.example.userlist_hw19.di

import com.example.userlist_hw19.data.user_list.service.UserListService
import com.example.userlist_hw19.data.user_page.service.UserPageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val USER_LIST_BASE_URL = "https://run.mocky.io/v3/"

    private const val USER_PAGE_BASE_URL = "https://reqres.in/api/"

    @Singleton
    @Provides
    @Named("UserListRetrofitClient")
    fun provideUserListRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(USER_LIST_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserList(@Named("UserListRetrofitClient") retrofit: Retrofit): UserListService =
        retrofit.create(UserListService::class.java)

    @Singleton
    @Provides
    @Named("UserPageRetrofitClient")
    fun provideUserPageRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(USER_PAGE_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserPage(@Named("UserPageRetrofitClient") retrofit: Retrofit): UserPageService =
        retrofit.create(UserPageService::class.java)
}
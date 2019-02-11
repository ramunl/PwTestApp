package ru.pwtest.pwapp.feature.usersList.di

import dagger.Module

@Module
data class UserViewModule (
    val id: Int,
    val balance: Int?,
    val name: String?,
    val email: String?)
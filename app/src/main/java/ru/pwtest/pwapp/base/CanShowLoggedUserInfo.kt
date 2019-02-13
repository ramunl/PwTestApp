package ru.pwtest.pwapp.base

import ru.pwtest.pwapp.model.UserViewModel

interface CanShowLoggedUserInfo {

    fun updateLoggedUserInfo(userViewModel: UserViewModel)
}
package ru.pwtest.pwapp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.pwtest.domainLayer.UserLoginUseCase
import ru.pwtest.pwapp.view.LoginView
import javax.inject.Inject

@InjectViewState
class UserLoginPresenter @Inject constructor(private val userLoginUseCase: UserLoginUseCase) : MvpPresenter<LoginView>() {

    fun login(login: String, pass: String) {
        userLoginUseCase.login(login, pass)
    }

    fun logout() {
        userLoginUseCase.logout()
    }
}

package ru.pwtest.domain.repository

import io.reactivex.Single
import ru.pwtest.dataLayer.network.AppApi
import ru.pwtest.dataLayer.session.UserSession
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val apiApp: AppApi,
    private val userSession: UserSession
) : AuthRepo {

    override fun signUp(email: String, password: String, username: String) = apiApp.signUp(
            email = email,
            password = password,
            username = username)
            .doAfterSuccess { userSession.authModel = it }
            .ignoreElement()


    override fun logOut() = userSession.clearAllData()

    override fun login(email: String, password: String) =
        apiApp.login(email, password)
            .doAfterSuccess { userSession.authModel = it }
            .ignoreElement()


    override fun isAuth(): Single<Boolean> = Single.fromCallable {
        userSession.isLoggedIn()
    }

}
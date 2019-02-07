package ru.pwtest.dataLayer.repository

import ru.pwtest.dataLayer.network.AppApi
import ru.pwtest.dataLayer.session.UserSession
import andrey.murzin.travelmate.domain.entity.AuthEntity
import io.reactivex.Completable
import io.reactivex.Single
import ru.pwtest.domainLayer.repository.AuthRepo
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val apiApp: AppApi,
    private val userSession: UserSession
) : AuthRepo {

    override fun signUp(email: String, password: String, username: String): Completable {
       return apiApp.signUp(
            email = email,
            password = password,
            username = username
        )
    }


    override fun login(email: String, password: String) =
            apiApp.login(email, password)
                    .doAfterSuccess { userSession.authEntity = AuthEntity(it.token) }
                    .ignoreElement()


    override fun isAuth(): Single<Boolean> = Single.fromCallable { userSession.isLoggedIn() }

}
package ru.pwtest.domainLayer.repository

import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepo {

    fun isAuth(): Single<Boolean>
    fun login(email: String, password: String): Completable
    fun signUp(email: String, password: String, username: String): Completable
}
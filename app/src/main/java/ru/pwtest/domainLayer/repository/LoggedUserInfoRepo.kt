package ru.pwtest.domainLayer.repository
import io.reactivex.Single
import ru.pwtest.domainLayer.entity.UserEntity

interface LoggedUserInfoRepo {

    fun getLoggedUserInfo(): Single<UserEntity>
}
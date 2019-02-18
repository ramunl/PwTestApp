package ru.pwtest.domain.repository
import io.reactivex.Single
import ru.pwtest.domain.entity.UserEntity

interface LoggedUserInfoRepo {
    fun getLoggedUserInfo(): Single<UserEntity>
}
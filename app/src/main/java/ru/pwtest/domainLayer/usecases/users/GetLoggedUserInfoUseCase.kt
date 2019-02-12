package ru.pwtest.domainLayer.usecases.users

import io.reactivex.Single
import ru.pwtest.domainLayer.entity.UserEntity
import ru.pwtest.domainLayer.repository.LoggedUserInfoRepo
import ru.pwtest.domainLayer.usecases.base.SingleUseCase
import javax.inject.Inject

class GetLoggedUserInfoUseCase @Inject constructor(
    private val repo: LoggedUserInfoRepo
) : SingleUseCase<GetLoggedUserInfoUseCase.Param, UserEntity>() {

    override fun build(parameters: Param): Single<UserEntity> = repo.getLoggedUserInfo()

    data class Param(val count: Int = 0)

}
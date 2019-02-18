package ru.pwtest.domain.usecases.users

import io.reactivex.Single
import ru.pwtest.domain.entity.UserEntity
import ru.pwtest.domain.repository.LoggedUserInfoRepo
import ru.pwtest.domain.usecases.base.SingleUseCase
import javax.inject.Inject

class GetLoggedUserInfoUseCase @Inject constructor(
    private val repo: LoggedUserInfoRepo
) : SingleUseCase<GetLoggedUserInfoUseCase.Param, UserEntity>() {

    override fun build(parameters: Param): Single<UserEntity> = repo.getLoggedUserInfo()

    data class Param(val count: Int = 0)

}
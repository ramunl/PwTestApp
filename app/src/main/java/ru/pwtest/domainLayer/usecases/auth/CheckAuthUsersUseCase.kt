package ru.pwtest.domainLayer.usecases.auth

import ru.pwtest.domainLayer.repository.AuthRepo
import ru.pwtest.domainLayer.usecases.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class CheckAuthUsersUseCase @Inject constructor(
        private val authRepo: AuthRepo
) : SingleUseCase<CheckAuthUsersUseCase.Param, Boolean>() {

    override fun build(parameters: Param): Single<Boolean> = authRepo.isAuth()

    class Param
}
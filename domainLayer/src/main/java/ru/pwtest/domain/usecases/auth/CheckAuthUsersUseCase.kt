package ru.pwtest.domain.usecases.auth

import io.reactivex.Single
import ru.pwtest.domain.repository.AuthRepo
import ru.pwtest.domain.usecases.base.SingleUseCase
import javax.inject.Inject

class CheckAuthUsersUseCase @Inject constructor(
        private val authRepo: AuthRepo
) : SingleUseCase<CheckAuthUsersUseCase.Param, Boolean>() {
    override fun build(parameters: Param): Single<Boolean> = authRepo.isAuth()
    class Param
}
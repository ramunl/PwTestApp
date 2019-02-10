package ru.pwtest.domainLayer.usecases.auth

import io.reactivex.Completable
import ru.pwtest.domainLayer.repository.AuthRepo
import ru.pwtest.domainLayer.usecases.base.CompletableUseCase
import javax.inject.Inject

class SignInUseCase @Inject constructor(
        private val authRepo: AuthRepo
) : CompletableUseCase<SignInUseCase.Param>() {


    override fun build(parameters: Param): Completable = authRepo.login(
            email = parameters.email,
            password = parameters.password)

    data class Param(
            val email: String,
            val password: String)
}
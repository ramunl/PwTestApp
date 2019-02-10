package ru.pwtest.domainLayer.usecases.auth

import io.reactivex.Completable
import ru.pwtest.domainLayer.repository.AuthRepo
import ru.pwtest.domainLayer.usecases.base.CompletableUseCase
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
        private val authRepo: AuthRepo
) : CompletableUseCase<SignUpUseCase.Param>() {


    override fun build(parameters: Param): Completable = authRepo.signUp(
            password = parameters.password,
            email = parameters.email,
            username = parameters.username)


    data class Param(
            val email: String,
            val password: String,
            val username: String)
}
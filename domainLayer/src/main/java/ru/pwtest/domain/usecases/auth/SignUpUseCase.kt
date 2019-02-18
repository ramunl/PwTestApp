package ru.pwtest.domain.usecases.auth

import io.reactivex.Completable
import ru.pwtest.domain.repository.AuthRepo
import ru.pwtest.domain.usecases.base.CompletableUseCase
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
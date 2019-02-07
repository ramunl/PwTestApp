package ru.pwtest.domainLayer.usecases.auth

import ru.pwtest.domainLayer.usecases.base.CompletableUseCase
import io.reactivex.Completable
import ru.pwtest.domainLayer.repository.AuthRepo
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
        private val authRepo: AuthRepo
) : CompletableUseCase<SignUpUseCase.Param>() {


    override fun build(parameters: Param): Completable = authRepo.signUp(
            password = parameters.password,
            email = parameters.email,
            username = parameters.firsName)


    data class Param(
            val email: String,
            val password: String,
            val firsName: String)
}
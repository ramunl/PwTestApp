package ru.pwtest.domain.usecases.auth

import io.reactivex.Completable
import ru.pwtest.domain.repository.AuthRepo
import ru.pwtest.domain.usecases.base.CompletableUseCase
import javax.inject.Inject

class SignInSignOutUseCase @Inject constructor(
        private val authRepo: AuthRepo
) : CompletableUseCase<SignInSignOutUseCase.Param>() {


    override fun build(parameters: Param): Completable = authRepo.login(
            email = parameters.email,
            password = parameters.password)

    data class Param(
            val email: String,
            val password: String)

    fun logout() {
        authRepo.logOut()
    }
}
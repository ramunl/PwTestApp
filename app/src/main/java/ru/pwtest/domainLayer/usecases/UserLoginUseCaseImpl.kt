package ru.pwtest.domainLayer.usecases

import io.reactivex.Observable
import ru.pwtest.domainLayer.UserLoginUseCase
import ru.pwtest.domainLayer.model.UserLoginCreateServerResp


class UserLoginUseCaseImpl: UserLoginUseCase {

    override fun logout() {
       // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isTokenPresented(): Boolean {
        return false
    }

    override fun login(username: String, password: String): Observable<UserLoginCreateServerResp>? {
        return null
    }
}

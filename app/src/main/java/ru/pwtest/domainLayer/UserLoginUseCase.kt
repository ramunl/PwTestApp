package ru.pwtest.domainLayer

import io.reactivex.Observable
import ru.pwtest.domainLayer.model.UserLoginCreateServerResp


interface UserLoginUseCase {
    fun isTokenPresented():Boolean
    fun login(username: String, password: String): Observable<UserLoginCreateServerResp>?
    fun logout()
}

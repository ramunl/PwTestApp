package ru.pwtest.pwapp.presenter

import android.text.TextUtils
import com.arellomobile.mvp.InjectViewState
import io.reactivex.Observable
import javax.inject.Inject


@InjectViewState
class UserLoginPresenter @Inject constructor(
    private var httpReqManager: HttpReqManager,
    private var schedulerProvider: SchedulerProvider
) {

    private val prefs = prefs()

    fun isTokenPresented(): Boolean {
        val isToken: String? = prefs[TOKEN_STRING_KEY]
        return !TextUtils.isEmpty(isToken)
    }

    fun provideTerminalLogin(username: String, password: String): Observable<LoginDataModel?>? {
        loading = true
        return httpReqManager.requestLogin<LoginDataModel?>(LOGIN_PATH, username, password)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doFinally { loading = false }
            .map { result -> result} }
    }

package ru.pwtest.pwapp.feature.signIn.presenter

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.addTo
import ru.pwtest.domain.error.ErrorHandler
import ru.pwtest.domain.provider.SchedulersProvider
import ru.pwtest.domain.repository.ResRepo
import ru.pwtest.domain.usecases.auth.SignInSignOutUseCase
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.signIn.view.SignInView
import ru.pwtest.pwapp.feature.signUp.view.SignUpActivity
import javax.inject.Inject

@InjectViewState
class SignInPresenter @Inject constructor(

    private val signInUseCase: SignInSignOutUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    private val resRepo: ResRepo

) : BasePresenter<SignInView>() {


    fun auth(email: String, password: String) {
        signInUseCase.build(
            SignInSignOutUseCase.Param(
                email = email,
                password = password
            )
        )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .doOnSubscribe { viewState.showLoading(true) }
            .doFinally { viewState.showLoading(false) }
            .subscribe(
                { viewState.showSuccessMessage(resRepo.getString(R.string.sign_in_success)) },
                { viewState.showErrorMessage(errorHandler.getError(it)) }
            ).addTo(compositeDisposable)
    }

    fun goRegistration(context: Context) {
        context.startActivity(Intent(context, SignUpActivity::class.java))
    }
}

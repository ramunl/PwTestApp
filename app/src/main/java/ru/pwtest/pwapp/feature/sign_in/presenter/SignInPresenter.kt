package ru.pwtest.pwapp.feature.sign_in.presenter

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import ru.pwtest.dataLayer.repository.ResRepo
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.auth.SignInSignOutUseCase
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.sign_in.view.SignInView
import ru.pwtest.pwapp.feature.sign_up.view.SignUpActivity
import javax.inject.Inject

@InjectViewState
class SignInPresenter @Inject constructor(
    override val compositeDisposable: CompositeDisposable,
    private val signInUseCase: SignInSignOutUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    private val resRepo: ResRepo
) : BasePresenter<SignInView>() {

    override fun attachView(view: SignInView?) {
        super.attachView(view)
        view?.let { errorHandler.attachView(it) }
    }

    override fun detachView(view: SignInView) {
        super.detachView(view)
        errorHandler.onDetach()
    }

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
                { errorHandler.handleError(it) }
            ).addTo(compositeDisposable)
    }

    fun goRegistration(context: Context) {
        context.startActivity(Intent(context, SignUpActivity::class.java))
    }
}

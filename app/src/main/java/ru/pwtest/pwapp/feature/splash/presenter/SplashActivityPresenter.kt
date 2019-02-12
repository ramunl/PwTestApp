package ru.pwtest.pwapp.feature.splash.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.auth.CheckAuthUsersUseCase
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.splash.view.SplashView
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class SplashActivityPresenter @Inject constructor(
    override val compositeDisposable: CompositeDisposable,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    private val checkAuthUsersUseCase: CheckAuthUsersUseCase
) : BasePresenter<SplashView>() {

    companion object {
        const val timerTime: Long = 400
    }

    override fun detachView(view: SplashView) {
        super.detachView(view)
        errorHandler.onDetach()
    }

    override fun attachView(view: SplashView?) {
        super.attachView(view)
        view?.let { errorHandler.attachView(it) }
        startSplashTimer()
    }

    private fun startSplashTimer() {
        Single.timer(
            timerTime,
            TimeUnit.MILLISECONDS,
            schedulersProvider.ui()
        )
            .subscribeBy(
                onSuccess = { onNextScreen() },
                onError = {
                    Timber.e(it)
                })
            .addTo(compositeDisposable)
    }

    private fun onNextScreen() {
        Timber.d("onNextScreen")
        checkAuthUsersUseCase.build(CheckAuthUsersUseCase.Param())
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .subscribeBy(
                onSuccess = { isAuth ->
                    if (isAuth) {
                        goMain()
                    } else {
                        goAuth()
                    }
                },
                onError = { Timber.e(it) })
            .addTo(compositeDisposable)
    }

    private fun goAuth() {
        viewState.runSignInActivity()
    }

    private fun goMain() {
        viewState.runMainActivity()
    }

}

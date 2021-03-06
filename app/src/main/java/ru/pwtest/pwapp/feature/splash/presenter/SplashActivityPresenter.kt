package ru.pwtest.pwapp.feature.splash.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.Single
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import ru.pwtest.domain.error.ErrorHandler
import ru.pwtest.domain.provider.SchedulersProvider
import ru.pwtest.domain.usecases.auth.CheckAuthUsersUseCase
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.splash.view.SplashView
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class SplashActivityPresenter @Inject constructor(

    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    private val checkAuthUsersUseCase: CheckAuthUsersUseCase
) : BasePresenter<SplashView>() {

    companion object {
        const val timerTime: Long = 400
    }


    override fun attachView(view: SplashView?) {
        super.attachView(view)
        startSplashTimer()
    }

    private fun startSplashTimer() {
        Single.timer(
            timerTime,
            TimeUnit.MILLISECONDS,
            schedulersProvider.ui()
        ).subscribeBy(
                onSuccess = { onNextScreen() },
                onError = {Timber.e(it) })
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

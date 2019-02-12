package ru.pwtest.pwapp.feature.main.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.addTo
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.auth.SignInSignOutUseCase
import ru.pwtest.domainLayer.usecases.users.GetLoggedUserInfoUseCase
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.main.view.MainView
import ru.pwtest.pwapp.mapper.EntityViewModelMapper
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val loggedUserInfoUseCase: GetLoggedUserInfoUseCase,
    private val signInSignOutUseCase: SignInSignOutUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val viewModelMapper: EntityViewModelMapper,
    private val errorHandler: ErrorHandler

) : BasePresenter<MainView>() {

    override fun attachView(view: MainView?) {
        super.attachView(view)
        view?.let { errorHandler.attachView(it) }
    }

    override fun detachView(view: MainView) {
        super.detachView(view)
        errorHandler.onDetach()
    }

    fun refreshLoggedUserInfo() {
        loggedUserInfoUseCase.build(GetLoggedUserInfoUseCase.Param())
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .map { viewModelMapper.mapToViewModel(it) }
            .subscribe(
                { viewState.updateLoggedUserInfo(it) },
                {
                    errorHandler.handleError(it)
                    viewState.loggedUserInfoNotFetched()
                })
            .addTo(compositeDisposable)
    }

    fun logout() {
        signInSignOutUseCase.logout()
    }
}

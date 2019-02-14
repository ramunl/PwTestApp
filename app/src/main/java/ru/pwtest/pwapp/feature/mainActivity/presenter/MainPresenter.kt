package ru.pwtest.pwapp.feature.mainActivity.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.addTo
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.auth.SignInSignOutUseCase
import ru.pwtest.domainLayer.usecases.users.GetLoggedUserInfoUseCase
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.mainActivity.view.MainView
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


    fun getLoggedUserInfo() {

    }
    fun refreshLoggedUserInfo() {
        loggedUserInfoUseCase.build(GetLoggedUserInfoUseCase.Param())
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .map { viewModelMapper.mapToViewModel(it) }
            .doOnSubscribe { viewState.showLoading(true) }
            .doFinally { viewState.showLoading(false) }
            .subscribe({
                viewState.refreshLoggedUserInfoViews(it)
                viewState.showTransactionsHistoryFragment()
                viewState.enableUserControls(true)
            },
                {
                    viewState.showErrorMessage(errorHandler.getError(it))
                    viewState.enableUserControls(false)
                })
            .addTo(compositeDisposable)
    }

    fun logout() {
        signInSignOutUseCase.logout()
    }
}

package ru.pwtest.pwapp.feature.main.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.auth.SignInSignOutUseCase
import ru.pwtest.domainLayer.usecases.users.GetLoggedUserInfoUseCase
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.main.interactior.LoggedUserInteractor
import ru.pwtest.pwapp.feature.main.view.MainView
import ru.pwtest.pwapp.mapper.EntityViewModelMapper
import timber.log.Timber
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    override val compositeDisposable: CompositeDisposable,
    private val loggedUserInfoUseCase: GetLoggedUserInfoUseCase,
    private val signInSignOutUseCase: SignInSignOutUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val viewModelMapper: EntityViewModelMapper,
    private val loggedUserInteractor: LoggedUserInteractor

) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val defaultSelectedMenuItem = R.id.nav_history
        viewState.setDefaultSelectedMenuItem(defaultSelectedMenuItem)
        navigateTo(defaultSelectedMenuItem)
    }

    override fun attachView(view: MainView?) {
        super.attachView(view)
        refreshLoggedUserInfo()
        loggedUserInteractor.interactor
            .subscribeOn(schedulersProvider.ui())
            .subscribe({ refreshLoggedUserInfo() }, { Timber.d(it) })
            .addTo(compositeDisposable)

    }

    override fun detachView(view: MainView) {
        super.detachView(view)
    }

    private fun refreshLoggedUserInfo() {
        loggedUserInfoUseCase.build(GetLoggedUserInfoUseCase.Param())
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .map { viewModelMapper.mapToViewModel(it) }
            .doOnSuccess { viewState.updateLoggedUserInfo(it) }
            .doOnError { viewState.loggedUserInfoNotFetched() }
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun navigateTo(itemId: Int) {
        when (itemId) {
            R.id.nav_history -> viewState.showTransactionsHistoryFragment()
            R.id.nav_logout -> {
                signInSignOutUseCase.logout()
                viewState.logoutAccount()
            }
            R.id.nav_users -> viewState.showUsersListFragment()
        }
    }
}

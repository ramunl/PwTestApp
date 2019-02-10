package ru.pwtest.pwapp.feature.main.presenter

import android.view.MenuItem
import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import ru.pwtest.domainLayer.usecases.auth.CheckAuthUsersUseCase
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.main.view.MainView
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    override val disposable: CompositeDisposable,
    private val checkAuthUsersUseCase: CheckAuthUsersUseCase
) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showTransactionsHistoryFragment()
    }

    fun navigateTo(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_history -> viewState.showTransactionsHistoryFragment()
            R.id.nav_logout -> {
                checkAuthUsersUseCase.logout()
                viewState.logoutAccount()
            }
            R.id.nav_users -> viewState.showUsersListFragment()
        }
    }
}

package ru.pwtest.pwapp.feature.main.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.pwtest.pwapp.model.UserViewModel

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {

    //@StateStrategyType(OneExecutionStateStrategy::class)
    fun showUsersListFragment()

    //@StateStrategyType(OneExecutionStateStrategy::class)
    fun showTransactionsHistoryFragment()

    fun logoutAccount()

    fun updateLoggedUserInfo(userViewModel: UserViewModel)

    fun loggedUserInfoNotFetched()

    fun setDefaultSelectedMenuItem(menuId:Int)
}
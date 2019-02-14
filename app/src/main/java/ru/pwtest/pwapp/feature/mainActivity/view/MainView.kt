package ru.pwtest.pwapp.feature.mainActivity.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.pwtest.pwapp.base.CanShowLoading
import ru.pwtest.pwapp.base.CanShowLoggedUserInfo
import ru.pwtest.pwapp.base.CanShowMessage

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView, CanShowMessage, CanShowLoading, CanShowLoggedUserInfo {

    fun logoutAccount()
    fun showTransactionsHistoryFragment()
    fun enableUserControls(isEnabled:Boolean)
}
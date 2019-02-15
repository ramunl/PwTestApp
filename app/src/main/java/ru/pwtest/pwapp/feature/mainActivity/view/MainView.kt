package ru.pwtest.pwapp.feature.mainActivity.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.pwtest.pwapp.base.CanShowLoading
import ru.pwtest.pwapp.base.CanShowLoggedUserInfo
import ru.pwtest.pwapp.base.CanShowMessage


interface MainView : MvpView, CanShowMessage, CanShowLoading, CanShowLoggedUserInfo {

    @StateStrategyType(SkipStrategy::class)
    fun logoutAccount()

    @StateStrategyType(SkipStrategy::class)
    fun showTransactionsHistoryFragment()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun enableUserControls(isEnabled:Boolean)
}
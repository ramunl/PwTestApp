package ru.pwtest.pwapp.feature.main.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.pwtest.pwapp.base.CanShowLoggedUserInfo
import ru.pwtest.pwapp.base.CanShowMessage
import ru.pwtest.pwapp.model.UserViewModel

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView, CanShowMessage, CanShowLoggedUserInfo {

    fun setDefaultSelectedMenuItem(menuId:Int)
    fun logoutAccount()

}
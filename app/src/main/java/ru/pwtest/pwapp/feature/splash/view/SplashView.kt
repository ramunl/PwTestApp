package ru.pwtest.pwapp.feature.splash.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.pwtest.pwapp.base.CanShowLoading
import ru.pwtest.pwapp.base.CanShowMessage

@StateStrategyType(AddToEndSingleStrategy::class)
interface SplashView : MvpView, CanShowMessage, CanShowLoading {
    fun runSignInActivity()
    fun runMainActivity()
}
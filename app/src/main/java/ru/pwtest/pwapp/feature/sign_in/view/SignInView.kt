package ru.pwtest.pwapp.feature.sign_in.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.pwtest.pwapp.base.CanShowLoading
import ru.pwtest.pwapp.base.CanShowMessage

@StateStrategyType(AddToEndSingleStrategy::class)
interface SignInView : MvpView, CanShowMessage, CanShowLoading {

}
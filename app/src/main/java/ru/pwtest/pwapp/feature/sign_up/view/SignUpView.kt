package ru.pwtest.pwapp.feature.sign_up.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.pwtest.pwapp.base.CanShowLoading
import ru.pwtest.pwapp.base.CanShowMessage

@StateStrategyType(AddToEndSingleStrategy::class)
interface SignUpView : MvpView, CanShowLoading, CanShowMessage {

    fun emailValid()
    fun emailNotValid()
    fun passwordNotValid(error: String)
    fun passwordValid()
}
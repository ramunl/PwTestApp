package ru.pwtest.pwapp.feature.createTransaction.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.pwtest.pwapp.base.CanShowLoading
import ru.pwtest.pwapp.base.CanShowLoggedUserInfo
import ru.pwtest.pwapp.base.CanShowMessage

@StateStrategyType(AddToEndSingleStrategy::class)
interface CreateTransactionView : MvpView, CanShowMessage, CanShowLoading, CanShowLoggedUserInfo {
    fun setPwAmountWrongFormatErrorMessage(error:String? = null)
    fun setPwAmount(wpAmountVal: Int)
    fun enableMakeTransactionButton(enable: Boolean)
}
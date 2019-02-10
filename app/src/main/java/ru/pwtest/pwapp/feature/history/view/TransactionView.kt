package ru.pwtest.pwapp.feature.history.view

import com.arellomobile.mvp.MvpView
import ru.pwtest.pwapp.base.CanShowLoading
import ru.pwtest.pwapp.base.CanShowMessage
import ru.pwtest.pwapp.model.TransactionViewModel

interface TransactionView : MvpView, CanShowMessage, CanShowLoading {
    fun displayTransaction(transactionList: List<TransactionViewModel>)
}
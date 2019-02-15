package ru.pwtest.pwapp.feature.transactions.view

import com.arellomobile.mvp.MvpView
import ru.pwtest.pwapp.base.CanShowLoading
import ru.pwtest.pwapp.base.CanShowMessage
import ru.pwtest.pwapp.model.TransactionViewModel

interface TransactionsView : MvpView, CanShowMessage, CanShowLoading {
    fun displayTransaction(transactionList: List<TransactionViewModel>)
}
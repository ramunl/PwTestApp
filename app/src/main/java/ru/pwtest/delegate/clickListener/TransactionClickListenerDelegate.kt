package ru.pwtest.delegate.clickListener

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import ru.pwtest.pwapp.feature.createTransaction.view.CreateTransactionActivity
import ru.pwtest.pwapp.feature.mainActivity.view.MainActivity
import ru.pwtest.pwapp.model.TransactionViewModel
import ru.pwtest.pwapp.model.UserViewModel
import javax.inject.Inject

class TransactionClickListenerDelegate @Inject constructor(val activity: Activity) {

    var sender:UserViewModel? = null

    init {
        sender = activity.intent.getParcelableExtra(CreateTransactionActivity.senderParam)
        if(sender == null) {
            throw Exception("Sender param for TransactionClickListener not found!")
        }
    }

    fun onTransactionClicked(transaction: TransactionViewModel) {
        CreateTransactionActivity.start(
            activity as AppCompatActivity,
            sender!!,
            transaction,
            MainActivity.RepeatTransactionReqCode
        )
    }



}
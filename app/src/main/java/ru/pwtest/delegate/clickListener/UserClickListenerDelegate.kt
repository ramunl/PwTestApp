package ru.pwtest.delegate.clickListener

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import ru.pwtest.pwapp.feature.createTransaction.view.CreateTransactionActivity
import ru.pwtest.pwapp.feature.mainActivity.view.MainActivity
import ru.pwtest.pwapp.model.UserViewModel
import javax.inject.Inject

class UserClickListenerDelegate @Inject constructor( val activity: Activity) {

    var sender:UserViewModel? = null

    init {
        sender = activity.intent.getParcelableExtra(CreateTransactionActivity.senderParam)
        if(sender == null) {
            throw Exception("Sender param for UserClickListener not found!")
        }
    }

    fun onUserClicked(userViewModel: UserViewModel) {
        CreateTransactionActivity.start(
            activity as AppCompatActivity,
            sender!!,
            userViewModel,
            MainActivity.CreateNewTransactionReqCode
        )
    }



}
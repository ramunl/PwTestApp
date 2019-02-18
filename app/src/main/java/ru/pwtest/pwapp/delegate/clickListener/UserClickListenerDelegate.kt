package ru.pwtest.pwapp.delegate.clickListener

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import ru.pwtest.pwapp.feature.createTransaction.view.CreateTransactionActivity
import ru.pwtest.pwapp.feature.mainActivity.view.MainActivity
import ru.pwtest.pwapp.model.UserViewModel
import javax.inject.Inject

class UserClickListenerDelegate @Inject constructor( val activity: Activity) {


    fun onUserClicked(userViewModel: UserViewModel) {
        CreateTransactionActivity.start(
            activity as AppCompatActivity,
            userViewModel,
            MainActivity.CreateNewTransactionReqCode
        )
    }



}
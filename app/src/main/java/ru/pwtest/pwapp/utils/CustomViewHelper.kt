package ru.pwtest.pwapp.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.layout_user_balance.view.*
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.model.UserViewModel


fun updateLoggedUserInfoFromViewModel(loggedUserInfo: View, userViewModel: UserViewModel?) {
    val context = loggedUserInfo.context
    if (userViewModel != null) {
        loggedUserInfo.visibility = VISIBLE
        loggedUserInfo.loggedUserName.text = String.format(context.getString(R.string.user_name_format), userViewModel.name)
        loggedUserInfo.loggedUserBalance.text = String.format(
            context.getString(R.string.pw_balance_format),
            userViewModel.balance,
            context.getString(R.string.currency)
        )
    } else {
        loggedUserInfo.visibility = GONE
    }
}
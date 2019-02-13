package ru.pwtest.pwapp.utils

import android.widget.TextView
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.model.UserViewModel


fun setLoggedUserInfoFromModel(loggedUserName: TextView, loggedUserBalance: TextView, userViewModel: UserViewModel) {
    val context = loggedUserName.context
    loggedUserName.text = String.format(context.getString(R.string.user_name_format), userViewModel.name)
    loggedUserBalance.text = String.format(context.getString(R.string.pw_balance_format), userViewModel.balance, context.getString(R.string.currency))
}
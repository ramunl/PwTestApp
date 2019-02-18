package ru.pwtest.dataLayer.session

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.pwtest.common.scope.PerApplication
import ru.pwtest.dataLayer.delegate.SharedPrefDelegate
import ru.pwtest.dataLayer.ext.get
import ru.pwtest.dataLayer.ext.set
import ru.pwtest.dataLayer.model.LoggedUserModel
import ru.pwtest.dataLayer.model.TransactionModel
import javax.inject.Inject

@PerApplication
class UserPersonalDataTemp @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {
    private val USER_NAME_KEY = "USER_NAME"
    private val BALANCE_KEY = "BALANCE"

    var userName: String? = null
        get() = field ?: sharedPreferences[USER_NAME_KEY]

    var balance: Int? = null
        get() = field ?: sharedPreferences[BALANCE_KEY]

    fun update(model: LoggedUserModel) {
        userName = model.loggedUser.username
        balance = model.loggedUser.balance
        sharedPreferences[USER_NAME_KEY] = userName
        sharedPreferences[BALANCE_KEY] = balance
    }

    fun update(model: TransactionModel) {
        userName = model.username
        balance = model.balance
        sharedPreferences.edit().putString(USER_NAME_KEY, userName).apply()
        sharedPreferences.edit().putInt(BALANCE_KEY, balance!!).apply()
    }

}
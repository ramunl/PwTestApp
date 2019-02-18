package ru.pwtest.dataLayer.session

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.pwtest.dataLayer.delegate.SharedPrefDelegate
import ru.pwtest.common.scope.PerApplication
import ru.pwtest.dataLayer.model.AuthModel
import ru.pwtest.dataLayer.model.LoggedUserModel
import timber.log.Timber
import javax.inject.Inject

@PerApplication
class UserPersonalDataTemp @Inject constructor(
    sharedPreferences: SharedPreferences,
    gson: Gson
) {

    var loggedUserModel: LoggedUserModel? by SharedPrefDelegate(LoggedUserModel::class.java, gson, sharedPreferences)


}
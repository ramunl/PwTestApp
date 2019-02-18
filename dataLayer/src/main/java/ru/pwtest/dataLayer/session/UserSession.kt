package ru.pwtest.dataLayer.session

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.pwtest.dataLayer.delegate.SharedPrefDelegate
import ru.pwtest.common.scope.PerApplication
import ru.pwtest.dataLayer.model.AuthModel
import timber.log.Timber
import javax.inject.Inject

@PerApplication
class UserSession @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    gson: Gson
) {

    var authModel: AuthModel? by SharedPrefDelegate(AuthModel::class.java, gson, sharedPreferences)

    fun clearAllData() {
        authModel = null
        sharedPreferences.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean {
        Timber.d("authModel = $authModel")
        return authModel != null
    }

}
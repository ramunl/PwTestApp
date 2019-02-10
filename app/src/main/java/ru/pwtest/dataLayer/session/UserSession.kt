package ru.pwtest.dataLayer.session

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.pwtest.delegate.SharedPrefDelegate
import ru.pwtest.domainLayer.entity.AuthEntity
import ru.pwtest.pwapp.di.PerApplication
import timber.log.Timber
import javax.inject.Inject

@PerApplication
class UserSession @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {

    var authEntity: AuthEntity? by SharedPrefDelegate(AuthEntity::class.java, gson, sharedPreferences)

    fun clearAllData() {
        authEntity = null
        sharedPreferences.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean {
        Timber.d("authEntity = $authEntity")
        return authEntity != null
    }

}
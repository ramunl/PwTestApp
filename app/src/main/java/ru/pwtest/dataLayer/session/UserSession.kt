package ru.pwtest.dataLayer.session

import andrey.murzin.travelmate.domain.entity.AuthEntity
import android.content.SharedPreferences
import com.google.gson.Gson
import ru.pwtest.delegate.SharedPrefDelegate
import ru.pwtest.pwapp.di.PerApplication
import javax.inject.Inject

@PerApplication
class UserSession @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson
) {

    var authEntity: AuthEntity? by SharedPrefDelegate(AuthEntity::class.java, gson, sharedPreferences)

    fun clearAllData() {
        authEntity = null
    }

    fun isLoggedIn(): Boolean = authEntity != null
}
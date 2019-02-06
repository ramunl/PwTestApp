package ru.pwtest.pwapp.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.pwtest.pwapp.utils.PreferenceHelper.prefs
import javax.inject.Singleton


@Module
class UtilsModule {

    @Provides
    @Singleton
    fun providePrefs(context:Context): SharedPreferences {
        return prefs(context)
    }

}
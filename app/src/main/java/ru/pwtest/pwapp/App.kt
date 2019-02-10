package ru.pwtest.pwapp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ru.pwtest.pwapp.di.DaggerAppComponent
import timber.log.Timber


class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)
}
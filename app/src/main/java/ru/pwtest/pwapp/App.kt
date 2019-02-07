package ru.pwtest.pwapp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import ru.pwtest.pwapp.di.DaggerAppComponent


class App : DaggerApplication() {

    @Override
    override fun onCreate() {
        super.onCreate()
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)
}
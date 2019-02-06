package ru.pwtest.pwapp

import android.app.Application
import ru.pwtest.pwapp.di.component.AppComponent
import ru.pwtest.pwapp.di.component.DaggerAppComponent
import ru.pwtest.pwapp.di.module.AppModule


class PwApp : Application() {


    var appComponent: AppComponent? = null
        private set

    @Override
    override fun onCreate() {
        super.onCreate()
        appComponent = buildComponent()
    }

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}
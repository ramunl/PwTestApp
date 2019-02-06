package ru.pwtest.pwapp.di.component

import dagger.Component
import ru.pwtest.pwapp.di.module.AppModule
import ru.pwtest.pwapp.di.module.UtilsModule
import ru.pwtest.pwapp.view.MainActivity
import javax.inject.Singleton


@Component(modules = [AppModule::class, UtilsModule::class] )
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}
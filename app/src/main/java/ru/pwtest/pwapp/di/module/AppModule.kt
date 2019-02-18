package ru.pwtest.pwapp.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import ru.pwtest.domain.provider.AppSchedulersProvider
import ru.pwtest.domain.provider.SchedulersProvider
import ru.pwtest.pwapp.App
import ru.pwtest.common.scope.PerApplication

@Module
abstract class AppModule {

    @Module
    companion object {
        @PerApplication
        @Provides
        @JvmStatic
        fun provideContext(application: App): Context = application

        @PerApplication
        @Provides
        @JvmStatic
        fun provideSchedulers(): SchedulersProvider = AppSchedulersProvider()

        @PerApplication
        @Provides
        @JvmStatic
        fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

        @PerApplication
        @Provides
        @JvmStatic
        fun provideSharedPreferences(context: Context): SharedPreferences =
                context.getSharedPreferences(App::class.java.simpleName, Context.MODE_PRIVATE)

    }

}

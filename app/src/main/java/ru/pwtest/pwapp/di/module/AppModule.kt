package ru.pwtest.pwapp.di.module

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import ru.pwtest.domainLayer.provider.AppSchedulersProvider
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.pwapp.App
import ru.pwtest.pwapp.di.PerApplication

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
        fun provideGson(): Gson = GsonBuilder().create()

        @PerApplication
        @Provides
        @JvmStatic
        fun provideSharedPreferences(context: Context): SharedPreferences =
                context.getSharedPreferences(App::class.java.simpleName, Context.MODE_PRIVATE)

    }


}

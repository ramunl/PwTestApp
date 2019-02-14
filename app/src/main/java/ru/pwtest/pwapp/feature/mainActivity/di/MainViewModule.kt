package ru.pwtest.pwapp.feature.mainActivity.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.pwtest.delegate.toolbar.ToolbarDelegate
import ru.pwtest.pwapp.di.PerActivity
import ru.pwtest.pwapp.di.PerFragment
import ru.pwtest.pwapp.feature.history.di.TransactionViewModule
import ru.pwtest.pwapp.feature.history.view.LoggedUserTransactionsFragment
import ru.pwtest.pwapp.feature.mainActivity.view.MainActivity

@Module
abstract class MainViewModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerActivity
        fun provideToolbarDelegate(activity: MainActivity) = ToolbarDelegate(activity)

    }

    @PerFragment
    @ContributesAndroidInjector(modules = [TransactionViewModule::class])
    abstract fun provideTransactionFragmentFactory(): LoggedUserTransactionsFragment


}

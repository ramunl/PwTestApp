package ru.pwtest.pwapp.feature.mainActivity.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.pwtest.pwapp.delegate.clickListener.TransactionClickListenerDelegate
import ru.pwtest.pwapp.delegate.toolbar.ToolbarDelegate
import ru.pwtest.common.scope.PerActivity
import ru.pwtest.common.scope.PerFragment
import ru.pwtest.pwapp.feature.mainActivity.view.MainActivity
import ru.pwtest.pwapp.feature.transactions.di.TransactionsViewModule
import ru.pwtest.pwapp.feature.transactions.view.TransactionsFragment

@Module
abstract class MainViewModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerActivity
        fun provideToolbarDelegate(activity: MainActivity) = ToolbarDelegate(activity)

        @JvmStatic
        @Provides
        @PerActivity
        fun provideTransactionClickListenerDelegate(activity: MainActivity) = TransactionClickListenerDelegate(activity)

    }

    @PerFragment
    @ContributesAndroidInjector(modules = [TransactionsViewModule::class])
    abstract fun provideTransactionFragmentFactory(): TransactionsFragment


}

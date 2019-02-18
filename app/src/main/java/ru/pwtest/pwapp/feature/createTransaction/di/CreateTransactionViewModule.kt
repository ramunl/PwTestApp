package ru.pwtest.pwapp.feature.createTransaction.di

import dagger.Module
import dagger.Provides
import ru.pwtest.common.scope.PerActivity
import ru.pwtest.pwapp.delegate.toolbar.ToolbarDelegate
import ru.pwtest.pwapp.feature.createTransaction.view.CreateTransactionActivity

@Module
abstract class CreateTransactionViewModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerActivity
        fun provideToolbarDelegate(activity: CreateTransactionActivity) = ToolbarDelegate(activity)

    }
}

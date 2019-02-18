package ru.pwtest.pwapp.feature.transactions.di

import dagger.Module
import dagger.Provides
import ru.pwtest.pwapp.delegate.itemDecorator.RecyclerViewItemDecorator
import ru.pwtest.common.scope.PerFragment
import ru.pwtest.pwapp.feature.mainActivity.view.MainActivity

@Module
abstract class TransactionsViewModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerFragment
        fun provideRecyclerViewHorizontalItemDecorator(activity: MainActivity) = RecyclerViewItemDecorator(activity)
    }
}
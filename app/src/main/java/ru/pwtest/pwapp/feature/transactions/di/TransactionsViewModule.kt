package ru.pwtest.pwapp.feature.transactions.di

import dagger.Module
import dagger.Provides
import ru.pwtest.delegate.itemDecorator.RecyclerViewItemDecorator
import ru.pwtest.pwapp.di.PerFragment
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
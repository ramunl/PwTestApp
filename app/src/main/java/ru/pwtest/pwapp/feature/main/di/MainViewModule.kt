package ru.pwtest.pwapp.feature.main.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.pwtest.delegate.toolbar.ToolbarDelegate
import ru.pwtest.pwapp.di.PerActivity
import ru.pwtest.pwapp.di.PerFragment
import ru.pwtest.pwapp.feature.usersList.di.UserViewModule
import ru.pwtest.pwapp.feature.usersList.view.UsersListFragment
import ru.pwtest.pwapp.feature.history.di.TransactionViewModule
import ru.pwtest.pwapp.feature.history.view.TransactionFragment
import ru.pwtest.pwapp.feature.main.view.MainActivity

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
    abstract fun provideTransactionFragmentFactory(): TransactionFragment


    @PerFragment
    @ContributesAndroidInjector(modules = [UserViewModule::class])
    abstract fun provideFilteredUserListFragmentFactory(): UsersListFragment

}

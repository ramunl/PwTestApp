package ru.pwtest.pwapp.feature.main.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.subjects.BehaviorSubject
import ru.pwtest.delegate.toolbar.ToolbarDelegate
import ru.pwtest.pwapp.di.PerActivity
import ru.pwtest.pwapp.di.PerFragment
import ru.pwtest.pwapp.feature.history.di.TransactionViewModule
import ru.pwtest.pwapp.feature.history.view.LoggedUserTransactionsFragment
import ru.pwtest.pwapp.feature.main.view.MainActivity
import ru.pwtest.pwapp.feature.usersList.di.UserViewModule
import ru.pwtest.pwapp.feature.usersList.view.UsersListFragment

@Module
abstract class MainViewModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerActivity
        fun provideToolbarDelegate(activity: MainActivity) = ToolbarDelegate(activity)


        @PerActivity
        @JvmStatic
        @Provides
        fun provideInteractor(): BehaviorSubject<Void> = BehaviorSubject.create()
    }

    @PerFragment
    @ContributesAndroidInjector(modules = [TransactionViewModule::class])
    abstract fun provideTransactionFragmentFactory(): LoggedUserTransactionsFragment


    @PerFragment
    @ContributesAndroidInjector(modules = [UserViewModule::class])
    abstract fun provideFilteredUserListFragmentFactory(): UsersListFragment

}

package ru.pwtest.pwapp.feature.selectUserActivity.di

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import ru.pwtest.delegate.toolbar.ToolbarDelegate
import ru.pwtest.pwapp.di.PerActivity
import ru.pwtest.pwapp.di.PerFragment
import ru.pwtest.pwapp.feature.selectUserActivity.view.SelectUserActivity
import ru.pwtest.pwapp.feature.usersList.di.UserViewModule
import ru.pwtest.pwapp.feature.usersList.view.UsersListFragment

@Module
abstract class SelectUserViewModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerActivity
        fun provideToolbarDelegate(activity: SelectUserActivity) = ToolbarDelegate(activity)

    }

    @PerFragment
    @ContributesAndroidInjector(modules = [UserViewModule::class])
    abstract fun provideFilteredUserListFragmentFactory(): UsersListFragment

}

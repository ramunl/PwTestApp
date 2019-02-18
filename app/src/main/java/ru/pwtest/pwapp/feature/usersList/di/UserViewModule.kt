package ru.pwtest.pwapp.feature.usersList.di

import dagger.Module
import dagger.Provides
import ru.pwtest.pwapp.delegate.clickListener.UserClickListenerDelegate
import ru.pwtest.pwapp.delegate.itemDecorator.RecyclerViewItemDecorator
import ru.pwtest.common.scope.PerFragment
import ru.pwtest.pwapp.feature.selectUserActivity.view.SelectUserActivity

@Module
abstract class UserViewModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        @PerFragment
        fun provideRecyclerViewHorizontalItemDecorator(activity: SelectUserActivity) = RecyclerViewItemDecorator(activity)

        @JvmStatic
        @Provides
        @PerFragment
        fun provideUserClickListenerDelegate(activity: SelectUserActivity) = UserClickListenerDelegate(activity)
    }
}
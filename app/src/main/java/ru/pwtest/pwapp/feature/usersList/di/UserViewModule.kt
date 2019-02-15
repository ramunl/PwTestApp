package ru.pwtest.pwapp.feature.usersList.di

import dagger.Module
import dagger.Provides
import ru.pwtest.delegate.clickListener.UserClickListenerDelegate
import ru.pwtest.delegate.itemDecorator.RecyclerViewItemDecorator
import ru.pwtest.pwapp.di.PerFragment
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
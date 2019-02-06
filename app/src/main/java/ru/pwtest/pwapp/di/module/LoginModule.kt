package ru.pwtest.pwapp.di.module

import dagger.Module
import dagger.Provides
import ru.pwtest.domainLayer.UserLoginUseCase
import ru.pwtest.pwapp.presenter.UserLoginPresenter


@Module
class LoginModule {

    @Provides
    fun provideUserLoginPresenter(userLoginUseCase: UserLoginUseCase): UserLoginPresenter{
        return UserLoginPresenter(userLoginUseCase)
    }

}
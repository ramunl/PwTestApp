package ru.pwtest.pwapp.di.module

import dagger.Module
import dagger.Provides
import ru.pwtest.domainLayer.UserLoginUseCase
import ru.pwtest.domainLayer.usecases.UserLoginUseCaseImpl



@Module
class UseCaseModule {

    @Provides
    fun provideUserLoginUseCase(): UserLoginUseCase {
        return UserLoginUseCaseImpl()
    }

}
package ru.pwtest.pwapp.di.module

import ru.pwtest.dataLayer.repository.AuthRepoImpl
import ru.pwtest.domainLayer.repository.AuthRepo
import dagger.Binds
import dagger.Module

@Module
interface RepoModule {

    @Binds
    fun provideAuthRepo(authRepo: AuthRepoImpl): AuthRepo

}
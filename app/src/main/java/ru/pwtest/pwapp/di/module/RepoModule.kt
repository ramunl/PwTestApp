package ru.pwtest.pwapp.di.module

import dagger.Binds
import dagger.Module
import ru.pwtest.dataLayer.repository.AuthRepoImpl
import ru.pwtest.dataLayer.repository.TransactionRepoImpl
import ru.pwtest.domainLayer.repository.AuthRepo
import ru.pwtest.domainLayer.repository.TransactionRepo

@Module
interface RepoModule {

    @Binds
    fun provideAuthRepo(authRepo: AuthRepoImpl): AuthRepo


    @Binds
    fun provideTransactionRepo(cityRepo: TransactionRepoImpl): TransactionRepo
}
package ru.pwtest.domain.di.module

import dagger.Binds
import dagger.Module
import ru.pwtest.dataLayer.errorHandler.HttpErrorMessageParser
import ru.pwtest.dataLayer.errorHandler.HttpErrorMessageParserImpl
import ru.pwtest.domain.repository.*

@Module
interface RepoModule {

    @Binds
    fun provideHttpErrorMessageParser(httpErrorMessageParser: HttpErrorMessageParserImpl): HttpErrorMessageParser

    @Binds
    fun provideAuthRepo(authRepo: AuthRepoImpl): AuthRepo

    @Binds
    fun provideTransactionRepo(transactionsRepo: TransactionRepoImpl): TransactionRepo

    @Binds
    fun provideFilteredUserListRepo(usersRepo: FilteredUserListRepoImpl): FilteredUserListRepo

    @Binds
    fun provideLoggedUserInfoRepo(loggedUserRepo: LoggedUserInfoRepoImpl): LoggedUserInfoRepo

}
package ru.pwtest.pwapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.pwtest.pwapp.di.PerActivity
import ru.pwtest.pwapp.feature.createTransaction.di.CreateTransactionViewModule
import ru.pwtest.pwapp.feature.createTransaction.view.CreateTransactionActivity
import ru.pwtest.pwapp.feature.mainActivity.di.MainViewModule
import ru.pwtest.pwapp.feature.mainActivity.view.MainActivity
import ru.pwtest.pwapp.feature.selectUserActivity.di.SelectUserViewModule
import ru.pwtest.pwapp.feature.selectUserActivity.view.SelectUserActivity
import ru.pwtest.pwapp.feature.signIn.di.SignInViewModule
import ru.pwtest.pwapp.feature.signIn.view.SignInActivity
import ru.pwtest.pwapp.feature.signUp.di.SignUpViewModule
import ru.pwtest.pwapp.feature.signUp.view.SignUpActivity
import ru.pwtest.pwapp.feature.splash.di.SplashViewModule
import ru.pwtest.pwapp.feature.splash.view.SplashActivity

@Module
interface AppBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [MainViewModule::class])
    fun provideMainActivityFactory(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [SignInViewModule::class])
    fun provideSignInActivityFactory(): SignInActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [SignUpViewModule::class])
    fun provideSignUpActivityFactory(): SignUpActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [SplashViewModule::class])
    fun provideSplashActivityFactory(): SplashActivity


    @PerActivity
    @ContributesAndroidInjector(modules = [CreateTransactionViewModule::class])
    fun provideCreateTransactionActivityFactory(): CreateTransactionActivity


    @PerActivity
    @ContributesAndroidInjector(modules = [SelectUserViewModule::class])
    fun provideSelectUserActivityFactory(): SelectUserActivity
}
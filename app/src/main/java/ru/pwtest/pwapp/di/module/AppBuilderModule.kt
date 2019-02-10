package ru.pwtest.pwapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.pwtest.pwapp.di.PerActivity
import ru.pwtest.pwapp.feature.main.di.MainViewModule
import ru.pwtest.pwapp.feature.main.view.MainActivity
import ru.pwtest.pwapp.feature.sign_in.di.SignInViewModule
import ru.pwtest.pwapp.feature.sign_in.view.SignInActivity
import ru.pwtest.pwapp.feature.sign_up.di.SignUpViewModule
import ru.pwtest.pwapp.feature.sign_up.view.SignUpActivity
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
}
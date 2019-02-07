package ru.pwtest.pwapp.di.module

import andrey.murzin.travelmate.presentation.feature.registration.di.SignUpViewModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import ru.pwtest.pwapp.di.PerActivity
import ru.pwtest.pwapp.feature.sign_in.di.SignInViewModule
import ru.pwtest.pwapp.feature.sign_in.view.SignInActivity
import ru.pwtest.pwapp.feature.sign_up.view.SignUpActivity

@Module
interface AppBuilderModule {


    @PerActivity
    @ContributesAndroidInjector(modules = [SignInViewModule::class])
    fun provideSignInActivityFactory(): SignInActivity


    @PerActivity
    @ContributesAndroidInjector(modules = [SignUpViewModule::class])
    fun provideSignUpActivityFactory(): SignUpActivity

}
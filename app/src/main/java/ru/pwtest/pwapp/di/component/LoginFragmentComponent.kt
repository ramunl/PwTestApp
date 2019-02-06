package ru.pwtest.pwapp.di.component

import dagger.Component
import ru.pwtest.pwapp.di.module.LoginModule
import ru.pwtest.pwapp.di.module.UseCaseModule
import ru.rian.dynamics.ui.fragments.LoginFragment
import javax.inject.Singleton


@Component(modules = [UseCaseModule::class, LoginModule::class] )
@Singleton
interface LoginFragmentComponent {
    fun inject(loginFragment: LoginFragment)
}
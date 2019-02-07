package ru.pwtest.pwapp.di

import javax.inject.Qualifier

/**
 * Created by olegshelyakin on 10/05/2018.
 */

@Qualifier
annotation class ComputationScheduler

@Qualifier
annotation class IoScheduler

@Qualifier
annotation class MainScheduler
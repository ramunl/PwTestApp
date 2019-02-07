package ru.pwtest.pwapp.view

import com.arellomobile.mvp.MvpView


//@StateStrategyType(AddToEndSingleStrategy::class)
interface LoginView : MvpView {
    fun showTimer()
}
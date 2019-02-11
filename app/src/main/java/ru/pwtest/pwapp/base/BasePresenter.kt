package ru.pwtest.pwapp.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    abstract val compositeDisposable: CompositeDisposable

    override fun detachView(view: View) {
        super.detachView(view)
        compositeDisposable.clear()
    }

}
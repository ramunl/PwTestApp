package ru.pwtest.pwapp.feature.main.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.main.view.MainView
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(override val disposable: CompositeDisposable
) : BasePresenter<MainView>() {

}

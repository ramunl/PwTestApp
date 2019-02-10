package ru.pwtest.pwapp.feature.usersList.presenter


import ru.pwtest.pwapp.feature.usersList.view.UsersListView
import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import ru.pwtest.pwapp.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class UsersListPresenter @Inject constructor(
        override val disposable: CompositeDisposable
) : BasePresenter<UsersListView>() {

}
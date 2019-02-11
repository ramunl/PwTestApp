package ru.pwtest.pwapp.feature.usersList.presenter


import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.users.GetFilteredUserListUseCase
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.usersList.view.UsersListView
import ru.pwtest.pwapp.mapper.EntityViewModelMapper
import javax.inject.Inject

@InjectViewState
class UsersListPresenter @Inject constructor(
    override val compositeDisposable: CompositeDisposable,
    private val userCase: GetFilteredUserListUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    private val viewModelMapper: EntityViewModelMapper
) : BasePresenter<UsersListView>() {

    override fun attachView(view: UsersListView?) {
        super.attachView(view)
        view?.let { errorHandler.attachView(it) }
    }

    override fun detachView(view: UsersListView) {
        super.detachView(view)
        errorHandler.onDetach()
        compositeDisposable.clear()
    }

    fun onSearchViewClosed() {
        viewState.submitLastQuery("")
    }

    fun getFilteredUserList(query: String?){
        if(!query.isNullOrEmpty()) {
            viewState.submitLastQuery(query)
            doGetFilteredUserList(query)
        }
    }

    private fun doGetFilteredUserList(filter: String?) {
        userCase.build(GetFilteredUserListUseCase.Param(filter))
            .flattenAsObservable { it }
            .map { viewModelMapper.mapToViewModel(it)}.toList()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .doOnSubscribe { viewState.showLoading(true) }
            .doFinally { viewState.showLoading(false) }
            .subscribe({ viewState.displayUsers(it) }, { errorHandler.handleError(it) })
            .also { compositeDisposable.add(it)  }
    }

}
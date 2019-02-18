package ru.pwtest.pwapp.feature.usersList.presenter


import com.arellomobile.mvp.InjectViewState
import ru.pwtest.domain.error.ErrorHandler
import ru.pwtest.domain.provider.SchedulersProvider
import ru.pwtest.domain.usecases.users.GetFilteredUserListUseCase
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.usersList.view.UsersListView
import ru.pwtest.pwapp.mapper.EntityViewModelMapper
import javax.inject.Inject

@InjectViewState
class UsersListPresenter @Inject constructor(

    private val userCase: GetFilteredUserListUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    private val viewModelMapper: EntityViewModelMapper

) : BasePresenter<UsersListView>() {

    fun getFilteredUserList(query: String){
        if(!query.isEmpty()) {
            userCase.build(GetFilteredUserListUseCase.Param(query))
                .flattenAsObservable { it }
                .map { viewModelMapper.mapToViewModel(it)}.toList()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doOnSubscribe { viewState.showLoading(true) }
                .doFinally { viewState.showLoading(false) }
                .subscribe({ viewState.displayUsers(it, query) }, { viewState.showErrorMessage(errorHandler.getError(it)) })
                .also { compositeDisposable.add(it)  }
        } else {
            viewState.displayUsers(emptyList(), query)
            viewState.showLoading(false)
        }
    }
}
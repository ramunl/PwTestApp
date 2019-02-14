package ru.pwtest.pwapp.feature.history.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.addTo
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.transaction.GetLoggedUserTransactionsUseCase
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.history.view.TransactionView
import ru.pwtest.pwapp.mapper.EntityViewModelMapper
import javax.inject.Inject

@InjectViewState
class TransactionPresenter @Inject constructor(

    private val getUserTransactionsUseCase: GetLoggedUserTransactionsUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    private val viewModelMapper: EntityViewModelMapper
) : BasePresenter<TransactionView>() {


    fun getTransactions() {
        getUserTransactionsUseCase.build(GetLoggedUserTransactionsUseCase.Param())
            .flattenAsObservable { it }
            .map { viewModelMapper.mapToViewModel(it) }
            .toList()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .doOnSubscribe { viewState.showLoading(true) }
            .doFinally { viewState.showLoading(false) }
            .subscribe({ viewState.displayTransaction(it) }, {
                viewState.showErrorMessage(errorHandler.getError(it)) })
            .addTo(compositeDisposable)
    }

}
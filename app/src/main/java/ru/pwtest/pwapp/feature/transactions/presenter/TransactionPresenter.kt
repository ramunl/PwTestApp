package ru.pwtest.pwapp.feature.transactions.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.addTo
import ru.pwtest.domain.error.ErrorHandler
import ru.pwtest.domain.provider.SchedulersProvider
import ru.pwtest.domain.usecases.transaction.GetLoggedUserTransactionsUseCase
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.transactions.view.TransactionsView
import ru.pwtest.pwapp.mapper.EntityViewModelMapper
import javax.inject.Inject

@InjectViewState
class TransactionPresenter @Inject constructor(

    private val getUserTransactionsUseCase: GetLoggedUserTransactionsUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    private val viewModelMapper: EntityViewModelMapper
) : BasePresenter<TransactionsView>() {


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
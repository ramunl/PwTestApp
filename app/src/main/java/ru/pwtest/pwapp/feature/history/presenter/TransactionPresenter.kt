package ru.pwtest.pwapp.feature.history.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.transaction.GetTransactionUseCase
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.history.view.TransactionView
import ru.pwtest.pwapp.mapper.TransactionViewModelMapper
import javax.inject.Inject

@InjectViewState
class TransactionPresenter @Inject constructor(override val disposable: CompositeDisposable,
                                               private val getTransactionUseCase: GetTransactionUseCase,
                                               private val schedulersProvider: SchedulersProvider,
                                               private val errorHandler: ErrorHandler,
                                               private val transactionViewModelMapper: TransactionViewModelMapper
) : BasePresenter<TransactionView>() {

    override fun attachView(view: TransactionView?) {
        super.attachView(view)
        view?.let { errorHandler.attachView(it) }
    }

    override fun detachView(view: TransactionView) {
        super.detachView(view)
        errorHandler.onDetach()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getTransactions()
    }

    private fun getTransactions() {
        getTransactionUseCase.build(GetTransactionUseCase.Param(10))
                .flattenAsObservable { it }
                .map { transactionViewModelMapper.mapToViewModel(it) }
                .toList()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doOnSubscribe { viewState.showLoading(true) }
                .doFinally { viewState.showLoading(false) }
                .subscribe({ viewState.displayTransaction(it) }, { errorHandler.handleError(it) })
    }

}
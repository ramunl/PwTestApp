package ru.pwtest.pwapp.feature.history.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.transaction.GetTransactionUseCase
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.history.view.TransactionView
import ru.pwtest.pwapp.mapper.EntityViewModelMapper
import javax.inject.Inject

@InjectViewState
class TransactionPresenter @Inject constructor(
    override val compositeDisposable: CompositeDisposable,
    private val getUserTransactionsUseCase: GetTransactionUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    private val viewModelMapper: EntityViewModelMapper
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

    fun getTransactions() {
        getUserTransactionsUseCase.build(GetTransactionUseCase.Param())
            .flattenAsObservable { it }
            .map { viewModelMapper.mapToViewModel(it) }
            .toList()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .doOnSubscribe { viewState.showLoading(true) }
            .doFinally { viewState.showLoading(false) }
            .subscribe({ viewState.displayTransaction(it) }, { errorHandler.handleError(it) })
            .addTo(compositeDisposable)
    }

}
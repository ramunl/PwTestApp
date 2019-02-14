package ru.pwtest.pwapp.feature.createTransaction.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.rxkotlin.addTo
import ru.pwtest.dataLayer.repository.ResRepo
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.transaction.CreateTransactionUseCase
import ru.pwtest.domainLayer.usecases.users.GetLoggedUserInfoUseCase
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.createTransaction.view.CreateTransactionView
import ru.pwtest.pwapp.mapper.EntityViewModelMapper
import ru.pwtest.pwapp.utils.PwValidator
import javax.inject.Inject

@InjectViewState
class CreateTransactionPresenter @Inject constructor(
    private val loggedUserInfoUseCase: GetLoggedUserInfoUseCase,
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val viewModelMapper: EntityViewModelMapper,
    private val errorHandler: ErrorHandler,
    private val resRepo: ResRepo

) : BasePresenter<CreateTransactionView>() {


    fun refreshLoggedUserInfo() {
        loggedUserInfoUseCase.build(GetLoggedUserInfoUseCase.Param())
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .map { viewModelMapper.mapToViewModel(it) }
            .subscribe({ model ->
                viewState.refreshLoggedUserInfoViews(model)
            }, { viewState.showErrorMessage(errorHandler.getError(it))})
            .addTo(compositeDisposable)
    }

    fun createTransaction(name: String, amount: Int) {
        createTransactionUseCase.build(
            CreateTransactionUseCase.Param(
                name = name,
                amount = amount))
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .doOnSubscribe { viewState.showLoading(true) }
            .doFinally {
                refreshLoggedUserInfo()
                viewState.showLoading(false)
            }
            .subscribe(
                { viewState.showSuccessMessage(resRepo.getString(R.string.transaction_success)) },
                { viewState.showErrorMessage(errorHandler.getError(it)) }
            ).addTo(compositeDisposable)
    }

    fun validatePwAmount(text: String) {
        var pwValidator = PwValidator(text)
        if (pwValidator.isValid) {
            viewState.setPwAmount(pwValidator.wpAmountVal)
            viewState.enableMakeTransactionButton(pwValidator.wpAmountVal > 0)
            viewState.setPwAmountWrongFormatErrorMessage(null)
        } else {
            viewState.enableMakeTransactionButton(false)
            viewState.setPwAmountWrongFormatErrorMessage(resRepo.getString(R.string.wrong_amount_format))
        }
    }

}

package ru.pwtest.pwapp.feature.sign_up.presenter

import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import ru.pwtest.dataLayer.repository.ResRepo
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.auth.SignUpUseCase
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.sign_up.view.SignUpView
import ru.pwtest.pwapp.utils.AuthValidator
import javax.inject.Inject

@InjectViewState
class SignUpPresenter @Inject constructor(
    override val compositeDisposable: CompositeDisposable,
    private val authValidator: AuthValidator,
    private val resRepo: ResRepo,
    private val signUpUseCase: SignUpUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BasePresenter<SignUpView>() {

    override fun attachView(view: SignUpView?) {
        super.attachView(view)
        view?.let { errorHandler.attachView(it) }
    }

    override fun detachView(view: SignUpView) {
        super.detachView(view)
        errorHandler.onDetach()
        compositeDisposable.clear()
    }


    fun validateEmail(email: CharSequence) {
        if (authValidator.isValidEmail(email)) viewState.emailValid()
        else viewState.emailNotValid()
    }

    fun validatePassword(password: CharSequence) {
        if (password.length < 3) {
            viewState.passwordNotValid(resRepo.getString(R.string.password_length))
        } else {
            viewState.passwordValid()
        }
    }

    fun signUp(email: String, password: String, username: String) {
        compositeDisposable.add(
        signUpUseCase.build(
            SignUpUseCase.Param(
                email = email,
                password = password,
                username = username
            )
        )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .doOnSubscribe { viewState.showLoading(true) }
            .doFinally { viewState.showLoading(false) }
            .subscribe({ viewState.showSuccessMessage(resRepo.getString(R.string.sign_up_success)) },
                { errorHandler.handleError(it) })
        )
    }

}

package ru.pwtest.pwapp.feature.sign_up.presenter

import ru.pwtest.pwapp.feature.sign_up.view.SignUpView
import andrey.murzin.travelmate.utils.AuthValidator
import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import ru.pwtest.dataLayer.repository.ResRepo
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.auth.SignUpUseCase
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class SignUpPresenter @Inject constructor(
    override val disposable: CompositeDisposable,
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
    }


    fun validateEmail(email: CharSequence) {
        if (authValidator.isValidEmail(email)) viewState.emailValid()
        else viewState.emailNotValid()
    }

    fun validatePassword(password: CharSequence) {
        if (password.length < 8) {
            viewState.passwordNotValid(resRepo.getString(R.string.password_length))
        } else {
            if (authValidator.isValidPassword(password)) {
                viewState.passwordValid()

            } else {
                viewState.passwordNotValid(resRepo.getString(R.string.invalid_password))
            }
        }
    }

    fun signUp(email: String, password: String, firstName: String) {
        signUpUseCase.build(SignUpUseCase.Param(
                email = email,
                password = password,
                firsName = firstName))
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .doOnSubscribe { viewState.showLoading(true) }
                .doFinally { viewState.showLoading(false) }
                .subscribe({viewState.showSuccessMessage(resRepo.getString(R.string.sign_up_success))  },
                    { errorHandler.handleError(it) })
    }

}

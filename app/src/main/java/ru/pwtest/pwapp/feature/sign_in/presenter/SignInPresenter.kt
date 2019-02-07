package ru.pwtest.pwapp.feature.sign_in.presenter

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import io.reactivex.disposables.CompositeDisposable
import ru.pwtest.dataLayer.repository.ResRepo
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.domainLayer.usecases.auth.SignInUseCase
import ru.pwtest.pwapp.base.BasePresenter
import ru.pwtest.pwapp.feature.sign_in.view.SignInView
import ru.pwtest.pwapp.feature.sign_up.view.SignUpActivity
import javax.inject.Inject

@InjectViewState
class SignInPresenter @Inject constructor(
    override val disposable: CompositeDisposable,
    private val signInUseCase: SignInUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val errorHandler: ErrorHandler,
    private val resRepo: ResRepo
) : BasePresenter<SignInView>() {

    companion object {
        const val RESULT_CODE_REGISTRATION = 11
    }

    override fun attachView(view: SignInView?) {
        super.attachView(view)
        view?.let { errorHandler.attachView(it) }
    }

    override fun detachView(view: SignInView) {
        super.detachView(view)
        errorHandler.onDetach()
    }

    fun auth(email: String, password: String) {
        signInUseCase.build(
            SignInUseCase.Param(
                email = email,
                password = password
            )
        )
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
            .doOnSubscribe { viewState.showLoading(true) }
            .doFinally { viewState.showLoading(false) }
            .subscribe(
                { /*router.navigateTo(Screen.Main)*/ },
                { errorHandler.handleError(it) }
            ).also { disposable.add(it) }
    }

    fun goRegistration(context: Context) {
        context.startActivity(Intent(context, SignUpActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        // router.removeResultListener(RESULT_CODE_REGISTRATION)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        /* router.setResultListener(RESULT_CODE_REGISTRATION) {
             if (it is Submit) {
                 viewState.showSuccessMessage(resRepo.getString(R.string.success))
             }
         }*/
    }

}

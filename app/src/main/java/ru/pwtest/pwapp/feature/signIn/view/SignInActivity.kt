package ru.pwtest.pwapp.feature.signIn.view

import android.support.annotation.LayoutRes
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import ru.pwtest.pwapp.delegate.SnackBarDelegate
import ru.pwtest.domain.error.ErrorHandler
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseActivity
import ru.pwtest.pwapp.feature.signIn.presenter.SignInPresenter
import ru.pwtest.pwapp.utils.ext.changeVisibility
import ru.pwtest.pwapp.utils.hideKeyboard
import javax.inject.Inject
import javax.inject.Provider


class SignInActivity : BaseActivity(), SignInView {

    @Inject
    lateinit var providerPresenter: Provider<SignInPresenter>

    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @ProvidePresenter
    fun providePresenter(): SignInPresenter = providerPresenter.get()

    @Inject
    lateinit var snackBarDelegate: SnackBarDelegate


    @LayoutRes
    override fun layoutRes() = R.layout.activity_sign_in

    override fun viewCreated(isRestoring:Boolean) {
        Observables.combineLatest(RxTextView.textChanges(emailEdit).skipInitialValue(),
                RxTextView.textChanges(passwordEdit).skipInitialValue())
        { email: CharSequence, password: CharSequence -> email.isNotEmpty() && password.isNotEmpty() }
                .distinctUntilChanged()
                .subscribe({ enableLoginButton(it)}, {enableLoginButton(false)})
                .also { compositeDisposable.add(it) }

        signInButton.setOnClickListener {
            hideKeyboard(this)
            presenter.auth(
                    email = emailEdit.text.toString(),
                    password = passwordEdit.text.toString())
        }

        createAccount.setOnClickListener { presenter.goRegistration(this) }

    }


    override fun showErrorMessage(errorParam: ErrorHandler.Param) {
        snackBarDelegate.showError(rootView, errorParam)
    }

    override fun showSuccessMessage(text: String) {
        snackBarDelegate.showSuccess(rootView, text, ::runMainActivity)
    }

    override fun enableCreateAccountButton(enabled: Boolean) {
        createAccount.isEnabled = enabled
    }

    override fun enableLoginButton(enabled: Boolean) {
        signInButton.isEnabled = enabled
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar?.changeVisibility(isLoading)
    }

}



package ru.pwtest.pwapp.feature.signUp.view

import android.support.annotation.LayoutRes
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import ru.pwtest.pwapp.delegate.SnackBarDelegate
import ru.pwtest.domain.error.ErrorHandler
import ru.pwtest.domain.provider.SchedulersProvider
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseToolbarActivity
import ru.pwtest.pwapp.feature.signUp.presenter.SignUpPresenter
import ru.pwtest.pwapp.utils.ext.changeVisibility
import ru.pwtest.pwapp.utils.hideKeyboard
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class SignUpActivity : BaseToolbarActivity(), SignUpView {


    companion object {
        const val DEBOUNCE_MAX_TIME: Long = 500L
    }

    @Inject
    lateinit var providerPresenter: Provider<SignUpPresenter>

    @Inject
    lateinit var schedulersProvider: SchedulersProvider

    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @Inject
    lateinit var snackBarDelegate: SnackBarDelegate

    @ProvidePresenter
    fun providePresenter(): SignUpPresenter = providerPresenter.get()


    @LayoutRes
    override fun layoutRes() = R.layout.activity_sign_up

    override fun viewCreated(isRestoring:Boolean) {
        setupActionBar(true)

        val firstNameEd = RxTextView.textChanges(emailEdit)
                .skipInitialValue()
                .debounce(DEBOUNCE_MAX_TIME, TimeUnit.MILLISECONDS)
                .map { it.isNotEmpty() }
                .observeOn(schedulersProvider.ui())
                .doOnNext {
                    if (it) userNameInput.error = ""
                    else userNameInput.error = resources.getString(R.string.empty_field)
                }

        val combinePassword = Observables.combineLatest(
                RxTextView.textChanges(passwordEdit)
                        .skipInitialValue()
                        .debounce(DEBOUNCE_MAX_TIME, TimeUnit.MILLISECONDS)
                        .observeOn(schedulersProvider.ui())
                        .doOnNext { presenter.validatePassword(it) },
                RxTextView.textChanges(repeatPasswordEdit)
                        .skipInitialValue()
                        .debounce(DEBOUNCE_MAX_TIME, TimeUnit.MILLISECONDS)
                        .observeOn(schedulersProvider.ui()))
        { password: CharSequence, confirm: CharSequence -> password.toString() == confirm.toString() }
                .doOnNext {
                    if (it) {
                        repeatPasswordInput.error = ""
                        repeatPasswordInput.isErrorEnabled = false
                    } else {
                        repeatPasswordInput.error = resources.getString(R.string.passwords_check)
                        repeatPasswordInput.isErrorEnabled = true
                    }
                }
                .map { repeatPasswordInput.isErrorEnabled.not().and(passwordInput.isErrorEnabled.not()) }

        val emailNameEd = RxTextView.textChanges(emailEdit)
            .skipInitialValue()
            .debounce(DEBOUNCE_MAX_TIME, TimeUnit.MILLISECONDS)
            .observeOn(schedulersProvider.ui())
            .doOnNext { presenter.validateEmail(it) }
            .map { emailInput.isErrorEnabled.not() }

        Observables.combineLatest(firstNameEd, emailNameEd, combinePassword)
        { item1, item2, item3  -> item1.and(item2).and(item3) }
                .subscribe({
                    enableSignUp(it)
                }, {}).also { compositeDisposable.add(it) }

        signUpButton.setOnClickListener {
            hideKeyboard(this)
            presenter.signUp(email = emailEdit.text.toString(),
                    password = passwordEdit.text.toString(),
                    username = usernameEdit.text.toString())
        }

    }

    private fun enableSignUp(isEnabled:Boolean) {
        signUpButton.isEnabled = isEnabled
    }



    override fun emailValid() {
        emailInput.error = ""
        emailInput.isErrorEnabled = false
    }

    override fun emailNotValid() {
        emailInput.error = resources.getString(R.string.incorrect_email)
        emailInput.isErrorEnabled = true
    }

    override fun passwordNotValid(error: String) {
        passwordInput.error = error
        passwordInput.isErrorEnabled = true
    }

    override fun passwordValid() {
        passwordInput.error = ""
        passwordInput.isErrorEnabled = false
    }

    override fun showLoading(isLoading: Boolean) {
        enableSignUp(!isLoading)
        progressBar.changeVisibility(isLoading)
    }

    override fun showErrorMessage(errorParam: ErrorHandler.Param) {
        snackBarDelegate.showError(rootView, errorParam)
    }

    override fun showSuccessMessage(text: String) {
        snackBarDelegate.showSuccess(rootView, text, ::runMainActivity)
    }

}

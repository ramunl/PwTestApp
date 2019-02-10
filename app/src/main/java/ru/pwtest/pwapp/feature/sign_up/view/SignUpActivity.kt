package ru.pwtest.pwapp.feature.sign_up.view

import android.content.Intent
import android.support.annotation.LayoutRes
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import ru.pwtest.delegate.SnackBarDelegate
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseActivity
import ru.pwtest.pwapp.feature.main.view.MainActivity
import ru.pwtest.pwapp.feature.sign_in.view.SignInActivity
import ru.pwtest.pwapp.feature.sign_up.presenter.SignUpPresenter
import ru.pwtest.pwapp.utils.ext.changeVisibility
import ru.pwtest.pwapp.utils.hideKeyboard
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class SignUpActivity : BaseActivity(), SignUpView {


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

    override fun viewCreated() {
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(true)
            setHomeButtonEnabled(true)
        }

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
                    if (it) enableSignUp()
                    else disableSignUp()
                }, {}).also { disposable.add(it) }

        signUpButton.setOnClickListener {
            hideKeyboard(this)
            presenter.signUp(email = emailEdit.text.toString(),
                    password = passwordEdit.text.toString(),
                    username = usernameEdit.text.toString())
        }

    }

    private fun disableSignUp() {
        signUpButton.isEnabled = false
    }

    private fun enableSignUp() {
        signUpButton.isEnabled = true
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

    override fun showLoading(flag: Boolean) {
        progressBar.changeVisibility(flag)
    }

    override fun showErrorMessage(text: String) {
        snackBarDelegate.showError(rootView, text)
    }

    override fun showSuccessMessage(text: String) {
        snackBarDelegate.showSuccess(rootView, text, ::runMainActivity)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

package ru.pwtest.pwapp.feature.signIn.view

import android.support.annotation.LayoutRes
import android.view.View.GONE
import android.view.View.VISIBLE
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.rxkotlin.Observables
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import ru.pwtest.delegate.SnackBarDelegate
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseActivity
import ru.pwtest.pwapp.feature.signIn.presenter.SignInPresenter
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
                .subscribe(
                        { if (it) enableLogin() else disableLogin() },
                        { disableLogin() }
                )
                .also { compositeDisposable.add(it) }

        signInButton.setOnClickListener {
            hideKeyboard(this)
            presenter.auth(
                    email = emailEdit.text.toString(),
                    password = passwordEdit.text.toString())
        }

        createAccount.setOnClickListener { presenter.goRegistration(this) }

    }

    private fun enableLogin() {
        signInButton.isEnabled = true
    }

    private fun disableLogin() {
        signInButton.isEnabled = false
    }

    override fun showErrorMessage(text: String, errCode: Int?) {
        snackBarDelegate.showError(rootView, text)
    }

    override fun showSuccessMessage(text: String) {
        snackBarDelegate.showSuccess(rootView, text, ::runMainActivity)
    }


    override fun showLoading(flag: Boolean) {
        progressBar.run {
            visibility = if(flag) VISIBLE else GONE
        }
    }

}


package ru.pwtest.pwapp.feature.splash.view

import android.content.Intent
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import ru.pwtest.pwapp.delegate.SnackBarDelegate
import ru.pwtest.domain.error.ErrorHandler
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseActivity
import ru.pwtest.pwapp.feature.signIn.view.SignInActivity
import ru.pwtest.pwapp.feature.splash.presenter.SplashActivityPresenter
import ru.pwtest.pwapp.utils.ext.changeVisibility
import javax.inject.Inject
import javax.inject.Provider

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : BaseActivity(), SplashView {

    @Inject
    lateinit var snackBarDelegate: SnackBarDelegate

    override fun layoutRes() = R.layout.activity_splash

    override fun viewCreated(isRestoring:Boolean) {
    }

    @Inject
    lateinit var providerPresenter: Provider<SplashActivityPresenter>

    @InjectPresenter
    lateinit var presenter: SplashActivityPresenter

    @ProvidePresenter
    fun providePresenter(): SplashActivityPresenter = providerPresenter.get()


    override fun showErrorMessage(errorParam: ErrorHandler.Param) {
        snackBarDelegate.showError(rootView, errorParam)
    }

    override fun showSuccessMessage(text: String) {
        snackBarDelegate.showSuccess(rootView, text, ::runMainActivity)
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar?.changeVisibility(isLoading)
    }

    override fun runSignInActivity() {
        startActivity(Intent(this, SignInActivity::class.java))
    }

}

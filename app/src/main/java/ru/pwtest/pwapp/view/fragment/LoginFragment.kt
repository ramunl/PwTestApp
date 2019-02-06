package ru.rian.dynamics.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.presenter.UserLoginPresenter
import ru.pwtest.pwapp.view.LoginView
import timber.log.Timber
import javax.inject.Inject


class LoginFragment : LoginView, BaseFragment() {

    override fun showTimer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val ARG_FEED_SOURCE = "feed_url"
        @JvmStatic
        fun newInstance() = LoginFragment().apply {
            arguments = Bundle().apply {
               // putSerializable(LoginFragment.ARG_FEED_SOURCE, feedSource)
            }
        }
    }

    @Inject
    lateinit var daggerPresenter: Lazy<UserLoginPresenter>

    @InjectPresenter
    lateinit var presenter: UserLoginPresenter

    @ProvidePresenter
    fun providePresenter(): UserLoginPresenter = daggerPresenter.value

    var loginButtonVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentComponent = DaggerLoginFragmentComponent
            .builder()
            .build()
        fragmentComponent.inject(this)
        if (savedInstanceState != null) {
            loginButtonVisible = savedInstanceState.getBoolean("loginButtonVisible", false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("loginButtonVisible", loginButtonVisible)
        //searchView?.let { outState.putString("query", it.query.toString()) }
        //outState.putParcelableArrayList("dataList", articlesAdapter.dataList)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    private val minLengthToProcess = 1

    /*fun checkLoginButtonVisibility() {
        if (showLoginButton()) {
            if (!loginButtonVisible) {
                updateLoginButtonState()
                loginButtonVisible = true
            }
        } else {
            if (loginButtonVisible) {
                updateLoginButtonState()
                loginButtonVisible = false
            }
        }
    }*/

    //private fun showLoginButton() = usernameEditText.length() > minLengthToProcess && passwordEditText.length() > minLengthToProcess

    private fun updateLoginButtonState() {
        (context as Activity).invalidateOptionsMenu()
    }


    private fun login() {
        Timber.d("login tapped")


    }

    override fun onStop() {
        super.onStop()
    }
}
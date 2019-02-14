package ru.pwtest.pwapp.feature.mainActivity.view

import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.layout_toolbar_collapsing.*
import kotlinx.android.synthetic.main.layout_user_balance.*
import ru.pwtest.delegate.SnackBarDelegate
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseToolbarActivity
import ru.pwtest.pwapp.feature.FragmentId
import ru.pwtest.pwapp.feature.history.view.LoggedUserTransactionsFragment
import ru.pwtest.pwapp.feature.mainActivity.presenter.MainPresenter
import ru.pwtest.pwapp.feature.selectUserActivity.view.SelectUserActivity
import ru.pwtest.pwapp.model.UserViewModel
import ru.pwtest.pwapp.utils.ext.changeVisibility
import ru.pwtest.pwapp.utils.replaceFragment
import ru.pwtest.pwapp.utils.updateLoggedUserInfoFromViewModel
import javax.inject.Inject
import javax.inject.Provider


class MainActivity : BaseToolbarActivity(), MainView, AppBarLayout.OnOffsetChangedListener {

    private var mMaxScrollSize: Int = 0
    private val percentageToShow = 20
    private var mIsImageHidden: Boolean = false
    private val maxScale = 1.1f


    override fun showLoading(flag: Boolean) {
        progressBar.run {
            visibility = if (flag) View.VISIBLE else View.GONE
        }
    }


    @Inject
    lateinit var snackBarDelegate: SnackBarDelegate

    @Inject
    lateinit var providerPresenter: Provider<MainPresenter>

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = providerPresenter.get()

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun viewCreated(isRestoring: Boolean) {
        setupActionBar(false)
        if (!isRestoring) {
            presenter.refreshLoggedUserInfo()
            toolbarUserInfo.scaleX = 0f
            toolbarUserInfo.scaleY = 0f
            toolbarUserInfoCollapsing.scaleX = maxScale
            toolbarUserInfoCollapsing.scaleY = maxScale
        }

        createTransaction.setOnClickListener { SelectUserActivity.start(this) }
        appBarLayout.addOnOffsetChangedListener(this)
    }

    override fun enableUserControls(isEnabled: Boolean) {
        createTransaction.isEnabled = isEnabled
        noServiceAvailableView.changeVisibility(!isEnabled)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.logout -> {
                presenter.logout()
                logoutAccount()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showTransactionsHistoryFragment() {
        replaceFragment(R.id.container, LoggedUserTransactionsFragment(), FragmentId.TRANSACTIONS_LIST_FRAGMENT_ID)
    }

    override fun logoutAccount() {
        snackBarDelegate.showSuccess(rootView, getString(R.string.logout_success), ::finish)
    }


    override fun refreshLoggedUserInfoViews(userViewModel: UserViewModel) {
        updateLoggedUserInfoFromViewModel(toolbarUserInfo, userViewModel)
        updateLoggedUserInfoFromViewModel(toolbarUserInfoCollapsing, userViewModel)
    }

    override fun showErrorMessage(errorParam: ErrorHandler.Param) {
        snackBarDelegate.showError(rootView, errorParam) { presenter.refreshLoggedUserInfo() }
    }

    override fun showSuccessMessage(text: String) {
    }


    override fun onOffsetChanged(appBarLayout: AppBarLayout, p1: Int) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.totalScrollRange

        val currentScrollPercentage = Math.abs(p1) * 100 / mMaxScrollSize

        if (currentScrollPercentage >= percentageToShow) {
            if (!mIsImageHidden) {
                mIsImageHidden = true
                ViewCompat.animate(toolbarUserInfo).scaleY(1f).scaleX(1f).start()
                ViewCompat.animate(toolbarUserInfoCollapsing).scaleY(0f).scaleX(0f).start()
                ViewCompat.animate(createTransaction).scaleY(0f).scaleX(0f).start()
            }
        }

        if (currentScrollPercentage < percentageToShow) {
            if (mIsImageHidden) {
                mIsImageHidden = false
                ViewCompat.animate(toolbarUserInfo).scaleY(0f).scaleX(0f).start()
                ViewCompat.animate(toolbarUserInfoCollapsing).scaleY(maxScale).scaleX(maxScale).start()
                ViewCompat.animate(createTransaction).scaleY(1f).scaleX(1f).start()
            }
        }
    }

}

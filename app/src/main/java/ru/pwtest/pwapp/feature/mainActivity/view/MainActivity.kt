package ru.pwtest.pwapp.feature.mainActivity.view

import android.app.Activity
import android.content.Intent
import android.support.design.widget.AppBarLayout
import android.support.v4.view.ViewCompat
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.processphoenix.ProcessPhoenix
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.layout_toolbar_collapsing.*
import kotlinx.android.synthetic.main.layout_user_balance.*
import ru.pwtest.domain.error.ErrorHandler
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseToolbarActivity
import ru.pwtest.pwapp.delegate.SnackBarDelegate
import ru.pwtest.pwapp.feature.FragmentId
import ru.pwtest.pwapp.feature.createTransaction.view.CreateTransactionActivity.Companion.lastTransactionResult
import ru.pwtest.pwapp.feature.mainActivity.presenter.MainViewPresenter
import ru.pwtest.pwapp.feature.selectUserActivity.view.SelectUserActivity
import ru.pwtest.pwapp.feature.transactions.view.TransactionsFragment
import ru.pwtest.pwapp.model.TransactionViewModel
import ru.pwtest.pwapp.model.UserViewModel
import ru.pwtest.pwapp.utils.ext.changeVisibility
import ru.pwtest.pwapp.utils.findFragment
import ru.pwtest.pwapp.utils.replaceFragment
import ru.pwtest.pwapp.utils.updateBalanceFromViewModel
import ru.pwtest.pwapp.utils.updateLoggedUserInfoFromViewModel
import javax.inject.Inject
import javax.inject.Provider


class MainActivity : BaseToolbarActivity(), MainView, AppBarLayout.OnOffsetChangedListener {
    companion object {
        const val CreateNewTransactionReqCode = 1
        const val RepeatTransactionReqCode = 2
    }

    private val percentageToShow = 20
    private var mIsImageHidden: Boolean = false
    private val maxScale = 1.1f
    private var mMaxScrollSize: Int = 0


    override fun showLoading(isLoading: Boolean) {
        progressBar?.changeVisibility(isLoading)
    }


    @Inject
    lateinit var snackBarDelegate: SnackBarDelegate

    @Inject
    lateinit var providerPresenter: Provider<MainViewPresenter>

    @InjectPresenter
    lateinit var presenter: MainViewPresenter

    @ProvidePresenter
    fun providePresenter(): MainViewPresenter = providerPresenter.get()

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

        createTransaction.setOnClickListener { SelectUserActivity.start(this, CreateNewTransactionReqCode) }
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
                logoutAccount()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showFragment() {
        replaceFragment(R.id.container, TransactionsFragment(), FragmentId.TRANSACTIONS_LIST_FRAGMENT_ID)
    }

    override fun logoutAccount() {
        presenter.logout()
        snackBarDelegate.showSuccess(
            rootView,
            getString(R.string.logout_success)
        ) { ProcessPhoenix.triggerRebirth(this) }
    }


    override fun refreshLoggedUserBalanceViews(viewModel: TransactionViewModel) {
        updateBalanceFromViewModel(toolbarUserInfo, viewModel)
        updateBalanceFromViewModel(toolbarUserInfoCollapsing, viewModel)
    }

    override fun refreshLoggedUserInfoViews(viewModel: UserViewModel) {
        updateLoggedUserInfoFromViewModel(toolbarUserInfo, viewModel)
        updateLoggedUserInfoFromViewModel(toolbarUserInfoCollapsing, viewModel)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CreateNewTransactionReqCode || requestCode == RepeatTransactionReqCode) {
                data?.let { presenter.onLoggedUserBalanceUpdated(it.getParcelableExtra(lastTransactionResult)) }
                val transactionsFragment =
                    findFragment(FragmentId.TRANSACTIONS_LIST_FRAGMENT_ID) as TransactionsFragment
                transactionsFragment.updateRecentTransactionsList()
            }
        }
    }
}

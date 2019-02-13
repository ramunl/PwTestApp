package ru.pwtest.pwapp.feature.main.view

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_user_balance.*
import ru.pwtest.delegate.SnackBarDelegate
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseToolbarActivity
import ru.pwtest.pwapp.feature.history.view.TransactionFragment
import ru.pwtest.pwapp.feature.main.presenter.MainPresenter
import ru.pwtest.pwapp.feature.usersList.view.UsersListFragment
import ru.pwtest.pwapp.model.UserViewModel
import ru.pwtest.pwapp.utils.replaceFragment
import ru.pwtest.pwapp.utils.updateLoggedUserInfoFromViewModel
import javax.inject.Inject
import javax.inject.Provider


class MainActivity : BaseToolbarActivity(), MainView, NavigationView.OnNavigationItemSelectedListener {

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
        userDataContainer.visibility = View.VISIBLE
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        if (!isRestoring) {
            presenter.refreshLoggedUserInfo()
            val defaultSelectedMenuItem = R.id.nav_history
            setDefaultSelectedMenuItem(defaultSelectedMenuItem)
            setDefaultSelectedMenuItem(R.id.nav_history)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigateTo(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun navigateTo(itemId: Int) {
        when (itemId) {
            R.id.nav_history -> showTransactionsHistoryFragment()
            R.id.nav_logout -> {
                presenter.logout()
                logoutAccount()
            }
            R.id.nav_users -> showUsersListFragment()
        }
    }

    private fun showUsersListFragment() {
        replaceFragment(R.id.container, UsersListFragment(), FragmentId.USERS_LIST_FRAGMENT_ID)
    }

    override fun setDefaultSelectedMenuItem(itemId: Int) {
        navigateTo(itemId)
        val item = nav_view.menu.findItem(itemId)
        item.isCheckable = true
        item.isChecked = true
    }

    private fun showTransactionsHistoryFragment() {
        replaceFragment(R.id.container, TransactionFragment(), FragmentId.TRANSACTIONS_LIST_FRAGMENT_ID)
    }

    override fun logoutAccount() {
        snackBarDelegate.showSuccess(coordinatorLayout, getString(R.string.logout_success), ::finish)
    }



    override fun refreshLoggedUserInfoViews(userViewModel: UserViewModel) {
        updateLoggedUserInfoFromViewModel(userDataContainer, userViewModel)
        // navHeaderTitle.text = String.format(getString(R.string.user_id_format), userViewModel.id.toString())
        // navHeaderSubTitle.text = String.format(getString(R.string.user_email_format), userViewModel.email)
    }

    override fun showErrorMessage(text: String, errCode: Int?) {
        snackBarDelegate.showError(rootView, text)
    }

    override fun showSuccessMessage(text: String) {
        //not used here
    }

}

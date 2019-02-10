package ru.pwtest.pwapp.feature.main.view

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import ru.pwtest.delegate.SnackBarDelegate
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseToolbarActivity
import ru.pwtest.pwapp.feature.history.view.TransactionFragment
import ru.pwtest.pwapp.feature.main.presenter.MainPresenter
import ru.pwtest.pwapp.feature.usersList.view.UsersListFragment
import ru.pwtest.pwapp.utils.replaceFragment
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

    override fun viewCreated() {
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        presenter.navigateTo(item)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun showUsersListFragment() {
        replaceFragment(R.id.container, UsersListFragment(), FragmentId.USERS_LIST_FRAGMENT_ID)
    }

    override  fun showTransactionsHistoryFragment() {
        replaceFragment(R.id.container, TransactionFragment(), FragmentId.TRANSACTIONS_LIST_FRAGMENT_ID)
    }

    override fun logoutAccount() {
        snackBarDelegate.showSuccess(rootView,getString(R.string.logout_success), ::finish)
    }
}

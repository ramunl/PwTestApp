package ru.pwtest.pwapp.feature.usersList.view

import android.content.Context
import android.content.Intent
import android.support.annotation.LayoutRes
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_create_transaction.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import ru.pwtest.delegate.SnackBarDelegate
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseActivity
import ru.pwtest.pwapp.feature.createTransaction.presenter.CreateTransactionPresenter
import ru.pwtest.pwapp.feature.main.view.FragmentId
import ru.pwtest.pwapp.feature.signUp.view.SignUpActivity
import ru.pwtest.pwapp.model.UserViewModel
import ru.pwtest.pwapp.utils.replaceFragment
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class UserListActivity : BaseActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, UserListActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun showUsersListFragment() {
        replaceFragment(R.id.container, UsersListFragment(), FragmentId.USERS_LIST_FRAGMENT_ID)
    }

    override fun viewCreated(isRestoring: Boolean) {
        if (!isRestoring) {
            showUsersListFragment()
        }
    }

    @LayoutRes
    override fun layoutRes() = R.layout.activity_main


}



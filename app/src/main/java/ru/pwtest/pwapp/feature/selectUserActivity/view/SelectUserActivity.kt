package ru.pwtest.pwapp.feature.selectUserActivity.view

import android.content.Context
import android.content.Intent
import android.support.annotation.LayoutRes
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseActivity
import ru.pwtest.pwapp.base.BaseToolbarActivity
import ru.pwtest.pwapp.feature.FragmentId
import ru.pwtest.pwapp.feature.usersList.view.UsersListFragment
import ru.pwtest.pwapp.utils.replaceFragment


class SelectUserActivity : BaseToolbarActivity() {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SelectUserActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun showUsersListFragment() {
        replaceFragment(R.id.container, UsersListFragment(), FragmentId.USERS_LIST_FRAGMENT_ID)
    }

    override fun viewCreated(isRestoring: Boolean) {
        setupActionBar(true)
        if (!isRestoring) {
            showUsersListFragment()
        }
    }

    @LayoutRes
    override fun layoutRes() = R.layout.activity_select_user


}



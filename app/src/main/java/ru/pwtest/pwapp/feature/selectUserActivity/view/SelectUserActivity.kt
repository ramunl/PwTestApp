package ru.pwtest.pwapp.feature.selectUserActivity.view

import android.app.Activity
import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.layout_user_balance.*
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseToolbarActivity
import ru.pwtest.pwapp.feature.FragmentId
import ru.pwtest.pwapp.feature.mainActivity.view.MainActivity
import ru.pwtest.pwapp.feature.usersList.view.UsersListFragment
import ru.pwtest.pwapp.utils.replaceFragment





class SelectUserActivity : BaseToolbarActivity() {

    companion object {
        @JvmStatic
        fun start(context: AppCompatActivity, requestCode:Int) {
            val intent = Intent(context, SelectUserActivity::class.java).apply {
            }
            context.startActivityForResult(intent, requestCode)
        }
    }

    private fun showUsersListFragment() {
        replaceFragment(R.id.container, UsersListFragment(), FragmentId.USERS_LIST_FRAGMENT_ID)
    }

    override fun viewCreated(isRestoring: Boolean) {
        setupActionBar(true)
        toolbarUserInfo.visibility = View.GONE
        if (!isRestoring) {
            showUsersListFragment()
        }
    }

    @LayoutRes
    override fun layoutRes() = R.layout.activity_select_user

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == MainActivity.CreateNewTransactionReqCode) {
            setResult(RESULT_OK, data)
            finish()
        }
    }
}



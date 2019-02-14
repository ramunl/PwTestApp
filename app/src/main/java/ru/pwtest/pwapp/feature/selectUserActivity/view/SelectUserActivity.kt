package ru.pwtest.pwapp.feature.selectUserActivity.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseToolbarActivity
import ru.pwtest.pwapp.feature.FragmentId
import ru.pwtest.pwapp.feature.createTransaction.view.CreateTransactionActivity.Companion.senderParam
import ru.pwtest.pwapp.feature.mainActivity.view.MainActivity
import ru.pwtest.pwapp.feature.usersList.view.UsersListFragment
import ru.pwtest.pwapp.model.UserViewModel
import ru.pwtest.pwapp.utils.replaceFragment





class SelectUserActivity : BaseToolbarActivity() {

    companion object {
        @JvmStatic
        fun start(context: AppCompatActivity, senderModel: UserViewModel,/* recipientModel: UserViewModel, */requestCode:Int) {
            val intent = Intent(context, SelectUserActivity::class.java).apply {
                //putExtra(recipientParam, recipientModel)
                putExtra(senderParam, senderModel)
            }
            context.startActivityForResult(intent, requestCode)
        }
    }

    private fun showUsersListFragment() {
        val fragment = UsersListFragment().apply {
            arguments = intent.extras
        }
        replaceFragment(R.id.container, fragment, FragmentId.USERS_LIST_FRAGMENT_ID)
    }

    override fun viewCreated(isRestoring: Boolean) {
        setupActionBar(true)
        if (!isRestoring) {
            showUsersListFragment()
        }
    }

    @LayoutRes
    override fun layoutRes() = R.layout.activity_select_user

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == MainActivity.requestCodeMakePayment) {
            setResult(RESULT_OK, data)
            finish()
        }
    }
}



package ru.pwtest.pwapp.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import dagger.android.support.AndroidSupportInjection
import ru.pwtest.pwapp.feature.sign_in.view.SignInActivity

abstract class BaseFragment : MvpAppCompatFragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutRes(), container, false)

    fun runSignInActivity() {
        startActivity(Intent(context, SignInActivity::class.java))
    }

    @LayoutRes
    protected abstract fun layoutRes(): Int

}

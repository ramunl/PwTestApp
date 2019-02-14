package ru.pwtest.delegate

import android.content.Intent
import android.support.design.widget.Snackbar
import android.view.View
import ru.pwtest.dataLayer.repository.ResRepo
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.feature.signIn.view.SignInActivity
import java.net.HttpURLConnection
import javax.inject.Inject

class SnackBarDelegate @Inject constructor(private val resRepo: ResRepo) {

    private var snackBar: Snackbar? = null

    fun showSuccess(view: View, successMsg: String, onDismiss: (() -> Unit)? = null) {
        snackBar = Snackbar.make(view, successMsg, Snackbar.LENGTH_SHORT)
        onDismiss?.let {
            snackBar?.addCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) {}
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    it.invoke()
                }
            })
        }
        snackBar?.show()
    }

    private fun showError(
        view: View,
        errParam: ErrorHandler.Param,
        actionText: String? = null,
        listener: View.OnClickListener? = null) {
        snackBar = Snackbar.make(
            view,
            errParam.errorMsg,
            if (listener == null) Snackbar.LENGTH_LONG else Snackbar.LENGTH_INDEFINITE
        )
        listener?.let {
            snackBar?.setAction(actionText, listener)
        }
        snackBar?.show()

    }

    fun showError(rootView: View, param: ErrorHandler.Param, tryAgainAction: (() -> Unit)? = null) {
        val context = rootView.context
        if (param.errCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            showError(
                rootView,
                param,
                resRepo.getString(R.string.sign_in),
                View.OnClickListener { context.startActivity(Intent(context, SignInActivity::class.java)) })
        } else {
            showError(rootView, param, resRepo.getString(R.string.try_again), View.OnClickListener { tryAgainAction })
        }
    }

    fun dismiss() {
        snackBar?.dismiss()
        snackBar = null
    }

}
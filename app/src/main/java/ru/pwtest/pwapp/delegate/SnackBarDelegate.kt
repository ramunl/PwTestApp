package ru.pwtest.pwapp.delegate

import android.content.Intent
import android.support.design.widget.Snackbar
import android.view.View
import ru.pwtest.domain.error.ErrorHandler
import ru.pwtest.domain.repository.ResRepo
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
        action: (() -> Unit)?
    ) {
        snackBar = Snackbar.make(
            view,
            errParam.errorMsg,
            if (action == null) Snackbar.LENGTH_LONG else Snackbar.LENGTH_INDEFINITE
        )
        action?.let {
            snackBar?.setAction(actionText) { action() }
        }


        snackBar?.show()

    }

    fun showError(rootView: View, param: ErrorHandler.Param, tryAgainAction: (() -> Unit)? = null) {
        val context = rootView.context
        if (param.errCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val msg = resRepo.getString(R.string.sign_in)
            if(context is SignInActivity) {
                showError(
                    rootView,
                    param,
                    msg,
                    tryAgainAction)
            } else {
                showError(
                    rootView,
                    param,
                    msg
                ) { context.startActivity(Intent(context, SignInActivity::class.java)) }
            }
        } else {
            showError(rootView, param, resRepo.getString(R.string.try_again), tryAgainAction)
        }
    }

    fun dismiss() {
        snackBar?.dismiss()
        snackBar = null
    }

}
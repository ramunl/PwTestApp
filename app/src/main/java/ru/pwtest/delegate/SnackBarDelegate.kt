package ru.pwtest.delegate

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import javax.inject.Inject

class SnackBarDelegate @Inject constructor(
    private val context: Context
) {

    fun showSuccess(view: View, text: String, onDismiss: (() -> Unit)? = null) {
        val snackBar: Snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        onDismiss?.let {
            snackBar.addCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) {}
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    it.invoke()
                }
            })
        }
        snackBar.show()
    }

    fun showError(view: View, text: String, actionText: String? = null, listener: View.OnClickListener? = null) {
        val snackBar: Snackbar =
            Snackbar.make(view, text, if (listener == null) Snackbar.LENGTH_LONG else Snackbar.LENGTH_INDEFINITE)
        listener?.let {
            snackBar.setAction(actionText, listener)
        }
        snackBar.show()
    }

}
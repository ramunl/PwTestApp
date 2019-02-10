package ru.pwtest.delegate

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import ru.pwtest.pwapp.R
import javax.inject.Inject

class SnackBarDelegate @Inject constructor(
    private val context: Context
) {

    fun showSuccess(view: View, text: String, onDismiss: (() -> Unit)? = null) {
        val snackBar: Snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        val snackBarView: View = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
        onDismiss?.let {
            snackBar.addCallback(object : Snackbar.Callback() {
                override fun onShown(sb: Snackbar?) { }
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    it.invoke()
                }
            })
        }
        snackBar.show()
    }

    fun showError(view: View, text: String) {
        val snackBar: Snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        val snackBarView: View = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        snackBar.show()
    }

}
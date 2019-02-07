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
    fun showSuccess(view: View, text: String) {
        val snackbar: Snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        val snackBarView: View = snackbar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
        snackbar.show()
    }

    fun showError(view: View, text: String) {
        val snackbar: Snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
        val snackBarView: View = snackbar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
        snackbar.show()
    }

}
package ru.pwtest.pwapp.feature.createTransaction.view

import android.content.Context
import android.content.Intent
import android.support.annotation.LayoutRes
import android.view.View.GONE
import android.view.View.VISIBLE
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_create_transaction.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.layout_user_balance.*
import ru.pwtest.delegate.SnackBarDelegate
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseActivity
import ru.pwtest.pwapp.feature.createTransaction.presenter.CreateTransactionPresenter
import ru.pwtest.pwapp.feature.signUp.view.SignUpActivity
import ru.pwtest.pwapp.model.UserViewModel
import ru.pwtest.pwapp.utils.isPwFormatValid
import ru.pwtest.pwapp.utils.setLoggedUserInfoFromModel
import timber.log.Timber
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class CreateTransactionActivity : BaseActivity(), CreateTransactionView {

    companion object {
        const val recipientNameParam = "name"
        @JvmStatic
        fun start(context: Context, name: String) {
            val intent = Intent(context, CreateTransactionActivity::class.java).apply {
                putExtra(recipientNameParam, name)
            }
            context.startActivity(intent)
        }
    }

    @Inject
    lateinit var schedulersProvider: SchedulersProvider

    @Inject
    lateinit var providerPresenter: Provider<CreateTransactionPresenter>

    @InjectPresenter
    lateinit var presenter: CreateTransactionPresenter

    @ProvidePresenter
    fun providePresenter(): CreateTransactionPresenter = providerPresenter.get()

    @Inject
    lateinit var snackBarDelegate: SnackBarDelegate


    @LayoutRes
    override fun layoutRes() = R.layout.activity_create_transaction

    override fun viewCreated(isRestoring: Boolean) {
        if (!isRestoring) {
            amountSendingEditText.setText(getString(R.string.default_amount))
            amountSendingEditText.requestFocus()
        }
        userDataContainer.visibility = VISIBLE
        val recipientName = intent.getStringExtra(recipientNameParam)
        recipientNameTextView.text = String.format(getString(R.string.recipient), recipientName)
        makeTransactionButton.setOnClickListener { presenter.createTransaction(recipientName, getSendingAmount()) }
        RxTextView.textChanges(amountSendingEditText)
            .debounce(SignUpActivity.DEBOUNCE_MAX_TIME, TimeUnit.MILLISECONDS)
            .observeOn(schedulersProvider.ui())
            .doOnNext {
                if(isPwFormatValid(amountSendingEditText.text.toString())) {
                    enableMakeTransactionButton(getSendingAmount() > 0)
                } else {
                    enableMakeTransactionButton(false)
                }
            }
            .doOnError {
                Timber.e(it)
                enableMakeTransactionButton(false)
            }
            .subscribe().addTo(compositeDisposable)

    }

    private fun getSendingAmount() = amountSendingEditText.text.toString().toInt()

    fun enterAmountErrorShow(showError: Boolean) {
        if (showError) {
            amountSendingTextInput.isErrorEnabled = true
            amountSendingTextInput.error = getString(R.string.wrong_amount_format)
        } else {
            amountSendingTextInput.isErrorEnabled = false
            amountSendingTextInput.error = ""
        }
    }

    private fun enableMakeTransactionButton(enable: Boolean) {
        makeTransactionButton.isEnabled = enable
    }

    override fun showErrorMessage(text: String, errCode: Int?) {
        snackBarDelegate.showError(rootView, text)
    }

    override fun showSuccessMessage(text: String) {
        snackBarDelegate.showSuccess(rootView, text, ::runMainActivity)
    }


    override fun showLoading(flag: Boolean) {
        progressBar.run {
            visibility = if (flag) VISIBLE else GONE
        }
    }

    override fun updateLoggedUserInfo(userViewModel: UserViewModel) {
        setLoggedUserInfoFromModel(loggedUserName, loggedUserBalance, userViewModel)
    }


}



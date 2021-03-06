package ru.pwtest.pwapp.feature.createTransaction.view

import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_create_transaction.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import kotlinx.android.synthetic.main.layout_user_balance.*
import ru.pwtest.domain.error.ErrorHandler
import ru.pwtest.domain.provider.SchedulersProvider
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseToolbarActivity
import ru.pwtest.pwapp.delegate.SnackBarDelegate
import ru.pwtest.pwapp.feature.createTransaction.presenter.CreateTransactionPresenter
import ru.pwtest.pwapp.feature.signUp.view.SignUpActivity
import ru.pwtest.pwapp.model.TransactionViewModel
import ru.pwtest.pwapp.model.UserViewModel
import ru.pwtest.pwapp.utils.ext.changeVisibility
import ru.pwtest.pwapp.utils.updateBalanceFromViewModel
import ru.pwtest.pwapp.utils.updateLoggedUserInfoFromViewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider
import kotlin.math.abs


class CreateTransactionActivity : BaseToolbarActivity(), CreateTransactionView {

    companion object {
        const val lastTransactionResult = "lastTransactionResult"
        const val recipientParam = "recipientParam"
        @JvmStatic
        fun start(
            context: AppCompatActivity,
            recipientModel: UserViewModel,
            requestCode: Int
        ) {
            val intent = Intent(context, CreateTransactionActivity::class.java).apply {
                putExtra(recipientParam, recipientModel)
            }
            context.startActivityForResult(intent, requestCode)
        }

        const val transactionParam = "transactionParam"

        @JvmStatic
        fun start(
            context: AppCompatActivity,
            transactionPrev: TransactionViewModel,
            requestCode: Int
        ) {
            val intent = Intent(context, CreateTransactionActivity::class.java).apply {
                putExtra(transactionParam, transactionPrev)
            }
            context.startActivityForResult(intent, requestCode)
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
        setupActionBar(true)
        if (!isRestoring) {
            presenter.refreshLoggedUserInfo()
            pwAmountEditText.requestFocus()
            getAmountFromIntent()?.let { presenter.validatePwAmount(it) }
        }

        recipientNameTextView.text = String.format(getString(R.string.recipient), getRecipientNameFromIntent())

        RxTextView.textChangeEvents(pwAmountEditText)
            .skipInitialValue()
            .debounce(SignUpActivity.DEBOUNCE_MAX_TIME, TimeUnit.MILLISECONDS)
            .observeOn(schedulersProvider.ui())
            .doOnNext {
                presenter.validatePwAmount(it.text().toString())
            }
            .doOnError {
                Timber.e(it)
                enableMakeTransactionButton(false)
            }
            .subscribe().addTo(compositeDisposable)

    }

    private fun getRecipientNameFromIntent(): String {
        val recipientModel: UserViewModel? = intent.getParcelableExtra(recipientParam)
        return if (recipientModel != null) {
            recipientModel.name
        } else {
            val prevTransactionModel: TransactionViewModel? = intent.getParcelableExtra(transactionParam)
            return prevTransactionModel!!.username // it must be initialised in this case!
        }
    }

    private fun getAmountFromIntent(): Int? {
        val prevTransactionModel: TransactionViewModel? = intent.getParcelableExtra(transactionParam)
        return prevTransactionModel?.run { abs(amount) }
    }


    private fun getPwAmountFromEdit() = pwAmountEditText.text.toString().toInt()

    override fun setPwAmountWrongFormatErrorMessage(error: String?) {
        if (error != null) {
            pwAmountTextInput.isErrorEnabled = true
            pwAmountTextInput.error = error
        } else {
            pwAmountTextInput.isErrorEnabled = false
        }
    }

    override fun setPwAmount(wpAmountVal: Int) {
        if (pwAmountEditText.text.toString() != wpAmountVal.toString()) {
            pwAmountEditText.setText(wpAmountVal.toString())
        }
    }

    override fun enableMakeTransactionButton(enable: Boolean) {
        makeTransactionButton.isEnabled = enable
    }

    override fun showErrorMessage(errorParam: ErrorHandler.Param) {
        snackBarDelegate.showError(rootView, errorParam)
    }

    override fun showSuccessMessage(text: String) {
        snackBarDelegate.showSuccess(rootView, text)
    }


    override fun showLoading(isLoading: Boolean) {
        progressBar?.changeVisibility(isLoading)
    }

    override fun refreshLoggedUserInfoViews(userViewModel: UserViewModel) {
        updateLoggedUserInfoFromViewModel(toolbarUserInfo, userViewModel)
        makeTransactionButton.setOnClickListener {
            presenter.createTransaction(
                getRecipientNameFromIntent(),
                getPwAmountFromEdit()
            )
        }
    }

    override fun transactionSucceeded(transactionViewModel: TransactionViewModel) {
        updateBalanceFromViewModel(toolbarUserInfo, transactionViewModel)
        val intent = Intent().apply {
            putExtra(lastTransactionResult, transactionViewModel)
        }
        setResult(RESULT_OK, intent)
    }
}



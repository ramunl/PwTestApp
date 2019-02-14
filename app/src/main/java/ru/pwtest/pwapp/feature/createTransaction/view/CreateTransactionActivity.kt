package ru.pwtest.pwapp.feature.createTransaction.view

import android.content.Context
import android.content.Intent
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
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
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.domainLayer.provider.SchedulersProvider
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseToolbarActivity
import ru.pwtest.pwapp.feature.createTransaction.presenter.CreateTransactionPresenter
import ru.pwtest.pwapp.feature.signUp.view.SignUpActivity
import ru.pwtest.pwapp.model.UserViewModel
import ru.pwtest.pwapp.utils.updateLoggedUserInfoFromViewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider


class CreateTransactionActivity : BaseToolbarActivity(), CreateTransactionView {

    companion object {
        const val senderParam = "senderParam"
        const val recipientParam = "recipientParam"
        @JvmStatic
        fun start(context: AppCompatActivity, senderModel: UserViewModel, recipientModel: UserViewModel, requestCode:Int) {
            val intent = Intent(context, CreateTransactionActivity::class.java).apply {
                putExtra(recipientParam, recipientModel)
                putExtra(senderParam, senderModel)
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
            pwAmountEditText.requestFocus()
            presenter.initLoggedUserInfo(intent.getParcelableExtra(senderParam))
        }
        val recipientModel: UserViewModel = intent.getParcelableExtra(recipientParam)
        recipientNameTextView.text = String.format(getString(R.string.recipient), recipientModel.name)
        makeTransactionButton.setOnClickListener {
            presenter.createTransaction(
                recipientModel.name,
                getPwAmountFromEdit()
            )
        }
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


    override fun showLoading(flag: Boolean) {
        progressBar.run {
            visibility = if (flag) VISIBLE else GONE
        }
    }

    override fun refreshLoggedUserInfoViews(userViewModel: UserViewModel) {
        updateLoggedUserInfoFromViewModel(toolbarUserInfo, userViewModel)
        val intent = Intent().apply {
            putExtra(senderParam, userViewModel)
        }
        setResult(RESULT_OK, intent)
    }
}



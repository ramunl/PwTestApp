package ru.pwtest.pwapp.feature.history.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import ru.pwtest.delegate.SnackBarDelegate
import ru.pwtest.delegate.error.ErrorHandler
import ru.pwtest.delegate.toolbar.ToolbarDelegate
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseFragment
import ru.pwtest.pwapp.feature.history.adapter.TransactionAdapter
import ru.pwtest.pwapp.feature.history.presenter.TransactionPresenter
import ru.pwtest.pwapp.model.TransactionViewModel
import ru.pwtest.pwapp.utils.ext.changeVisibility
import javax.inject.Inject
import javax.inject.Provider

class LoggedUserTransactionsFragment : BaseFragment(), TransactionView {


    @Inject
    lateinit var providerPresenter: Provider<TransactionPresenter>

    @InjectPresenter
    lateinit var presenter: TransactionPresenter

    @ProvidePresenter
    fun providePresenter(): TransactionPresenter = providerPresenter.get()

    @Inject
    lateinit var transactionAdapter: TransactionAdapter

    @Inject
    lateinit var snackBarDelegate: SnackBarDelegate

    @Inject
    lateinit var toolbarDelegate: ToolbarDelegate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyListTextView.text = getString(R.string.transactions_not_found)
        toolbarDelegate.changeTitle(resources.getString(R.string.recent_transactions))
        with(recyclerView) {
            val manager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            layoutManager = manager
            setHasFixedSize(true)
            adapter = transactionAdapter
        }
        if(savedInstanceState == null) {
            presenter.getTransactions()
        }
    }

    override fun layoutRes() = R.layout.fragment_recyclerview


    override fun showErrorMessage(errorParam: ErrorHandler.Param) {

    }

    override fun showSuccessMessage(text: String) {
        snackBarDelegate.showSuccess(rootView, text)
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.changeVisibility(isLoading)
        emptyListTextView.changeVisibility(transactionAdapter.itemCount == 0 && !isLoading)

    }

    override fun displayTransaction(transactionList: List<TransactionViewModel>) {
        transactionAdapter.setData(transactionList)
    }


    override fun onDetach() {
        super.onDetach()
        snackBarDelegate.dismiss()
    }
}
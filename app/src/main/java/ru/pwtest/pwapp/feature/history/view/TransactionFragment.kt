package ru.pwtest.pwapp.feature.history.view

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import ru.pwtest.delegate.SnackBarDelegate
import ru.pwtest.delegate.toolbar.ToolbarDelegate
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseFragment
import ru.pwtest.pwapp.feature.history.adapter.TransactionAdapter
import ru.pwtest.pwapp.feature.history.presenter.TransactionPresenter
import ru.pwtest.pwapp.model.TransactionViewModel
import ru.pwtest.pwapp.utils.ext.changeVisibility
import javax.inject.Inject
import javax.inject.Provider

class TransactionFragment : BaseFragment(), TransactionView {

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
        toolbarDelegate.changeTitle(resources.getString(R.string.history_list_of_transactions))
        with(recyclerView) {
            adapter = transactionAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun layoutRes() = R.layout.fragment_recyclerview


    override fun showErrorMessage(text: String) {
        snackBarDelegate.showError(rootView, text)
    }

    override fun showSuccessMessage(text: String) {
        snackBarDelegate.showSuccess(rootView, text)
    }

    override fun showLoading(flag: Boolean) {
        progressBar.changeVisibility(flag)
    }

    override fun displayTransaction(transactionList: List<TransactionViewModel>) {
        transactionAdapter.setData(transactionList)
    }

}
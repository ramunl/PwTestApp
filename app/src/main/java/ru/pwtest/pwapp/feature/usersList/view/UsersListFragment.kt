package ru.pwtest.pwapp.feature.usersList.view

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.layout_progressbar.*
import ru.pwtest.delegate.SnackBarDelegate
import ru.pwtest.delegate.toolbar.ToolbarDelegate
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseFragment
import ru.pwtest.pwapp.feature.usersList.adapter.FilteredUsersAdapter
import ru.pwtest.pwapp.feature.usersList.presenter.UsersListPresenter
import ru.pwtest.pwapp.model.UserViewModel
import ru.pwtest.pwapp.utils.ext.changeVisibility
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Provider


class UsersListFragment : BaseFragment(), UsersListView {

    @Inject
    lateinit var filteredUsersAdapter: FilteredUsersAdapter

    @Inject
    lateinit var snackBarDelegate: SnackBarDelegate

    @Inject
    lateinit var providerPresenter: Provider<UsersListPresenter>

    @InjectPresenter
    lateinit var presenter: UsersListPresenter

    @ProvidePresenter
    fun providePresenter(): UsersListPresenter = providerPresenter.get()

    @Inject
    lateinit var toolbarDelegate: ToolbarDelegate


    override fun layoutRes() = R.layout.fragment_recyclerview

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarDelegate.changeTitle("")
        emptyListTextView.text = getString(R.string.users_not_found)
        setHasOptionsMenu(true)
        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = filteredUsersAdapter
            val horizontalDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            val horizontalDivider = ContextCompat.getDrawable(context, R.drawable.list_divider)
            horizontalDecoration.setDrawable(horizontalDivider!!)
            addItemDecoration(horizontalDecoration)
        }

    }


    override fun showErrorMessage(text: String, errCode: Int?) {
        if (errCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            snackBarDelegate.showError(
                rootView,
                text,
                getString(R.string.sign_in),
                View.OnClickListener { runSignInActivity() })
        } else {
            snackBarDelegate.showError(
                rootView,
                text,
                getString(R.string.try_again),
                View.OnClickListener { presenter.getFilteredUserList(lastQuery)})
        }
    }

    override fun showSuccessMessage(text: String) {
        snackBarDelegate.showSuccess(rootView, text)
    }

    override fun showLoading(isLoading: Boolean) {
        progressBar.changeVisibility(isLoading)
        emptyListTextView.changeVisibility(filteredUsersAdapter.itemCount == 0 && !isLoading)
    }


    /**
     * if the screen rotates we get last query from our presenter
     */
    private var lastQuery: String = ""

    override fun displayUsers(itemList: List<UserViewModel>, query: String) {
        filteredUsersAdapter.setData(itemList)
        lastQuery = query
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.users_filter_menu, menu)
        initSearchView(menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }


    private fun initSearchView(menu: Menu) {
        val searchViewMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchViewMenuItem.actionView as SearchView
        searchView.setQuery(lastQuery, false)
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                presenter.getFilteredUserList(newText)
                return true
            }
        })
        //searchView.setOnCloseListener { presenter.onSearchViewClosed(); false }
    }

    override fun onDetach() {
        super.onDetach()
        snackBarDelegate.dismiss()
    }
}
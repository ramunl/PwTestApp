package ru.pwtest.pwapp.feature.usersList.view

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.pwtest.delegate.toolbar.ToolbarDelegate
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.base.BaseFragment
import ru.pwtest.pwapp.feature.usersList.presenter.UsersListPresenter
import javax.inject.Inject
import javax.inject.Provider

class UsersListFragment : BaseFragment(), UsersListView {

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
        toolbarDelegate.changeTitle(resources.getString(R.string.users_list))
    }
}
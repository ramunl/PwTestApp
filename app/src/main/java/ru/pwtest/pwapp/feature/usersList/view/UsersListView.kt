package ru.pwtest.pwapp.feature.usersList.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.pwtest.pwapp.base.CanShowLoading
import ru.pwtest.pwapp.base.CanShowMessage
import ru.pwtest.pwapp.model.UserViewModel

interface UsersListView : MvpView, CanShowMessage, CanShowLoading {

    @StateStrategyType(SingleStateStrategy::class)
    fun displayUsers(itemList: List<UserViewModel>, query: String)
}
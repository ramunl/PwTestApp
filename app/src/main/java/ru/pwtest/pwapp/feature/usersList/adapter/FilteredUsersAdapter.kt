package ru.pwtest.pwapp.feature.usersList.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_user.view.*
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.feature.createTransaction.view.CreateTransactionActivity
import ru.pwtest.pwapp.feature.usersList.view.UsersListFragment
import ru.pwtest.pwapp.model.UserViewModel
import ru.pwtest.pwapp.utils.ext.inflate
import javax.inject.Inject

class FilteredUsersAdapter (var userClickListener: UsersListFragment.UserClickListener)
    : RecyclerView.Adapter<FilteredUsersAdapter.ViewHolder>() {

    private var itemList: MutableList<UserViewModel> = mutableListOf()

    fun setData(transactionList: List<UserViewModel>) {
        this.itemList.clear()
        this.itemList.addAll(transactionList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder =
            ViewHolder(viewGroup.inflate(R.layout.list_item_user, false))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(itemList[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(userViewModel: UserViewModel) {
            with(itemView) {
                user_name.text = userViewModel.name
                user_id.text = userViewModel.id.toString()
                setOnClickListener { userClickListener.onUserClicked(userViewModel) }
            }
        }
    }
}
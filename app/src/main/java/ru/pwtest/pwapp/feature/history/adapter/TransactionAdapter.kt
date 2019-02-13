package ru.pwtest.pwapp.feature.history.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_transaction.view.*
import ru.pwtest.pwapp.R
import ru.pwtest.pwapp.model.TransactionViewModel
import ru.pwtest.pwapp.utils.ext.inflate
import javax.inject.Inject

class TransactionAdapter @Inject constructor() : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private var transactionList: MutableList<TransactionViewModel> = mutableListOf()

    fun setData(transactionList: List<TransactionViewModel>) {
        this.transactionList.clear()
        this.transactionList.addAll(transactionList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder =
            ViewHolder(viewGroup.inflate(R.layout.list_item_transaction, false))

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(transactionList[position])
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(transactionViewModel: TransactionViewModel) {
            with(itemView) {
                transaction_amount.text = transactionViewModel.amount.toString()
                transaction_balance.text = transactionViewModel.balance.toString()
                transaction_user_name.text = transactionViewModel.username
                transaction_date.text = transactionViewModel.date
            }
        }
    }
}
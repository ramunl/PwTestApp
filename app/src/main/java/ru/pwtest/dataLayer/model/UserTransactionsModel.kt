package ru.pwtest.dataLayer.model

import com.google.gson.annotations.SerializedName


data class UserTransactionsModel(
        @SerializedName("trans_token") var transactions: MutableList<TransactionModel>? = null
)

data class TransactionModel(
        @SerializedName("id")val id: Int,
        @SerializedName("username")val username: String?,
        @SerializedName("date")val date: Int?,
        @SerializedName("amount")val amount: Int?,
        @SerializedName("balance")val balance: Int?
)
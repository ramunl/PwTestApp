package ru.pwtest.dataLayer.model

import com.google.gson.annotations.SerializedName


data class LoggedUserTransactionsModel(
        @SerializedName("trans_token") var transactions: MutableList<TransactionModel>? = null
)
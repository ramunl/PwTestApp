package ru.pwtest.dataLayer.model

import com.google.gson.annotations.SerializedName


data class CreateTransactionRespModel(
        @SerializedName("trans_token") var transaction: TransactionModel
)
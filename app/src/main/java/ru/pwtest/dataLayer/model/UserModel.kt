package ru.pwtest.dataLayer.model

import com.google.gson.annotations.SerializedName


data class UserModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val username: String,
    @SerializedName("balance") val balance: Int,
    @SerializedName("email") val email: String?
)
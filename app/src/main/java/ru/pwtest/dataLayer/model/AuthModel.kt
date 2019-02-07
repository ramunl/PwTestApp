package ru.pwtest.dataLayer.model

import com.google.gson.annotations.SerializedName

data class AuthModel(
        @SerializedName("id_token")
        val token: String)
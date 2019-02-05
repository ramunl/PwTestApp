package ru.pwtest.pwapp.model

import com.google.gson.annotations.SerializedName

data class UserLoginCreate(
    @SerializedName("id_token") var token: String? = null
)
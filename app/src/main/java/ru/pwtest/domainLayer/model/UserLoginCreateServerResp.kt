package ru.pwtest.domainLayer.model

import com.google.gson.annotations.SerializedName

data class UserLoginCreateServerResp(
    @SerializedName("id_token") var token: String? = null
)
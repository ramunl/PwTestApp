package ru.pwtest.dataLayer.model

import com.google.gson.annotations.SerializedName


data class LoggedUserModel(
    @SerializedName("user_info_token") val loggedUser: UserModel
)
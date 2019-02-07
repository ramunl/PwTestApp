package ru.pwtest.dataLayer.network

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import ru.pwtest.dataLayer.model.AuthModel

interface AppApi {

    @POST("sessions/create")
    @FormUrlEncoded
    fun login(@Field("email") email: String,
              @Field("password") password: String): Single<AuthModel>

    @POST("users")
    @FormUrlEncoded
    fun signUp(@Field("email") email: String,
               @Field("password") password: String,
               @Field("username") username: String): Completable
}
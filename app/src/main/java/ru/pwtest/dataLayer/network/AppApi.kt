package ru.pwtest.dataLayer.network

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import ru.pwtest.dataLayer.model.AuthModel
import ru.pwtest.dataLayer.model.TransactionModel

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


    @GET("api/protected/transactions")
    fun getTransactions(): Single<List<TransactionModel>>

}
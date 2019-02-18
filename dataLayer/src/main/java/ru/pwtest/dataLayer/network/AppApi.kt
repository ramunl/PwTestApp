package ru.pwtest.dataLayer.network

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import ru.pwtest.dataLayer.model.*

interface AppApi {

    //Login
    @POST("sessions/create")
    @FormUrlEncoded
    fun login(@Field("email") email: String,
              @Field("password") password: String): Single<AuthModel>

    //Create/Register a user
    @POST("users")
    @FormUrlEncoded
    fun signUp(@Field("email") email: String,
               @Field("password") password: String,
               @Field("username") username: String):  Single<AuthModel>


    //List of logged user transactions
    @GET("api/protected/transactions")
    fun getTransactions(): Single<LoggedUserTransactionsModel>


    //Filtered User list
    @POST("/api/protected/users/list")
    @FormUrlEncoded
    fun filteredUserList(@Field("filter") filter: String?): Single<List<UserModel>>

    //Logged user info
    @GET ("/api/protected/user-info")
    fun loggedUserInfo(): Single<LoggedUserModel>


    //Create a transaction
    @POST("/api/protected/transactions")
    @FormUrlEncoded
    fun createTransaction(@Field("name") name: String,
               @Field("amount") amount: Int): Single<CreateTransactionRespModel>
}
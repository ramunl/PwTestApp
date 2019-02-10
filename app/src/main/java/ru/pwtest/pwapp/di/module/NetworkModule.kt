package ru.pwtest.pwapp.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.pwtest.dataLayer.network.AppApi
import ru.pwtest.dataLayer.network.AuthorizeInterceptor
import ru.pwtest.dataLayer.session.UserSession
import ru.pwtest.pwapp.di.PerApplication

@Module
class NetworkModule {

    companion object {
        const val BASE_URL: String = "http://193.124.114.46:3001/"
    }

    @PerApplication
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @PerApplication
    @Provides
    fun provideAuthInterceptor(userSession: UserSession) = AuthorizeInterceptor(userSession)

    @PerApplication
    @Provides
    fun provideOkHttpClient(logging: HttpLoggingInterceptor, authorizeInterceptor: AuthorizeInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(authorizeInterceptor)
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @PerApplication
    @Provides
    fun provideAppApi(httpClient: OkHttpClient): AppApi {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient)
                .baseUrl(BASE_URL)
                .build().create(AppApi::class.java)
    }

}

package ru.pwtest.dataLayer.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.pwtest.dataLayer.session.UserAuthDataTemp
import javax.inject.Inject

class AuthorizeInterceptor @Inject constructor(
        private val userSession: UserAuthDataTemp
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (userSession.isLoggedIn()) {
            builder.addHeader("Authorization", "Bearer ${userSession.authModel?.token}")
            return chain.proceed(builder.build())
        }

        return chain.proceed(builder.build())
    }
}
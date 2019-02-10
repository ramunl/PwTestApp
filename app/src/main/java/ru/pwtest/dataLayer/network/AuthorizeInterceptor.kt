package ru.pwtest.dataLayer.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.pwtest.dataLayer.session.UserSession
import javax.inject.Inject

class AuthorizeInterceptor @Inject constructor(
        private val userSession: UserSession
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (userSession.isLoggedIn()) {
            builder.addHeader("token_type", "bearer")
            builder.addHeader("access_token", "${userSession.authEntity?.token}")
            return chain.proceed(builder.build())
        }

        return chain.proceed(builder.build())
    }
}
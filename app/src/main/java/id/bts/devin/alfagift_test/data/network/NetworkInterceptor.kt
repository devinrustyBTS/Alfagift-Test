package id.bts.devin.alfagift_test.data.network

import id.bts.devin.alfagift_test.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class NetworkInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val modified = original.newBuilder().apply {
            addHeader("Content-Type", "application/json")
            addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
            method(original.method, original.body)
        }
        val request: Request = modified.build()
        return chain.proceed(request)
    }
}

object LoggingInterceptor {
    fun create(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}

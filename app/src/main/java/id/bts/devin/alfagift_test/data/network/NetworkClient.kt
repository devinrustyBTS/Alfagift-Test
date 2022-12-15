package id.bts.devin.alfagift_test.data.network

import id.bts.devin.alfagift_test.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NetworkClient @Inject constructor(
    private val client: OkHttpClient
) {
    fun <T> create(defClass: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(getConverter())
            .addCallAdapterFactory(getCallAdapter())
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()
        return retrofit.create(defClass)
    }

    private fun getConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    private fun getCallAdapter(): RxJava3CallAdapterFactory {
        return RxJava3CallAdapterFactory.create()
    }
}
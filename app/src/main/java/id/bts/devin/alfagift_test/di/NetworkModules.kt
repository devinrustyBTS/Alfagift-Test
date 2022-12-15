package id.bts.devin.alfagift_test.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.bts.devin.alfagift_test.data.network.LoggingInterceptor
import id.bts.devin.alfagift_test.data.network.NetworkClient
import id.bts.devin.alfagift_test.data.network.NetworkInterceptor
import id.bts.devin.alfagift_test.data.services.MovieService
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModules {

    @Provides
    @Singleton
    fun provideNetworkClient(
        interceptor: NetworkInterceptor
    ): OkHttpClient {
        val timeout = 3L
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(LoggingInterceptor.create())
            .callTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieService(client: OkHttpClient): MovieService {
        return NetworkClient(client).create(MovieService::class.java)
    }
}
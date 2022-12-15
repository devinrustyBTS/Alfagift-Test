package id.bts.devin.alfagift_test.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import id.bts.devin.alfagift_test.data.repositories.MovieRepositoryImpl
import id.bts.devin.alfagift_test.data.services.MovieService
import id.bts.devin.alfagift_test.domain.repositories.MovieRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(service: MovieService): MovieRepository {
        return MovieRepositoryImpl(service)
    }

}
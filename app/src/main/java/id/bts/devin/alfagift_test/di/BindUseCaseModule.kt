package id.bts.devin.alfagift_test.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import id.bts.devin.alfagift_test.domain.usecases.MovieUseCase
import id.bts.devin.alfagift_test.domain.usecases.MovieUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface BindUseCaseModule {

    @Binds
    fun bindMovieUseCase(impl: MovieUseCaseImpl): MovieUseCase
}
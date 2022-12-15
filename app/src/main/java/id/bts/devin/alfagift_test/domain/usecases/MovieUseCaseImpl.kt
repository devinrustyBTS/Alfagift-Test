package id.bts.devin.alfagift_test.domain.usecases

import androidx.paging.PagingData
import id.bts.devin.alfagift_test.data.utils.NetworkResult
import id.bts.devin.alfagift_test.domain.models.MovieDetailsModel
import id.bts.devin.alfagift_test.domain.models.MovieReviewsModel
import id.bts.devin.alfagift_test.domain.models.MovieVideosModel
import id.bts.devin.alfagift_test.domain.models.NowPlayingModel
import id.bts.devin.alfagift_test.domain.repositories.MovieRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.zipWith
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
): MovieUseCase {

    override suspend fun getNowPlayingMovies(): Flow<PagingData<NowPlayingModel>> {
        return movieRepository.getNowPlayingMovies()
    }

    override suspend fun getMovieDetails(movieId: Long): Flow<NetworkResult<MovieDetailsModel>> {
        return movieRepository.getMovieDetails(movieId)
    }

    override fun getMovieVideos(movieId: Long): Single<List<MovieVideosModel>> {
        return movieRepository.getMovieVideos(movieId)
    }

    override fun getMovieReviews(movieId: Long): Observable<PagingData<MovieReviewsModel>> {
        return movieRepository.getMovieReviews(movieId)
    }

}
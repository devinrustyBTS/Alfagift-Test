package id.bts.devin.alfagift_test.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava3.observable
import id.bts.devin.alfagift_test.data.models.MovieDetailsResponse
import id.bts.devin.alfagift_test.data.models.MovieReviewResultResponse
import id.bts.devin.alfagift_test.data.models.MovieVideosResultResponse
import id.bts.devin.alfagift_test.data.pagingsource.MovieReviewsPagingSource
import id.bts.devin.alfagift_test.data.pagingsource.NowPlayingPagingSource
import id.bts.devin.alfagift_test.data.services.MovieService
import id.bts.devin.alfagift_test.data.utils.NetworkResult
import id.bts.devin.alfagift_test.domain.models.MovieReviewsModel
import id.bts.devin.alfagift_test.domain.models.MovieVideosModel
import id.bts.devin.alfagift_test.domain.models.NowPlayingModel
import id.bts.devin.alfagift_test.domain.repositories.MovieRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieService
) : MovieRepository {

    override suspend fun getNowPlayingMovies(): Flow<PagingData<NowPlayingModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 10
            ),
        ) { NowPlayingPagingSource(service) }.flow.flowOn(Dispatchers.IO)
    }

    override suspend fun getMovieDetails(movieId: Long) = flow {
        emit(NetworkResult.Loading)
        try {
            val response = service.getMovieDetails(movieId)
            response.body()?.let { movie ->
                val data = MovieDetailsResponse.transform(movie)
                emit(NetworkResult.Success(data))
            } ?: run { throw HttpException(response) }
        } catch (throwable: Throwable) {
            emit(NetworkResult.Error(throwable))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieReviews(movieId: Long): Observable<PagingData<MovieReviewsModel>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 10
            ),
            pagingSourceFactory = { MovieReviewsPagingSource(service, movieId) }
        )
        return pager.observable.map { data ->
            data.map { MovieReviewResultResponse.transform(it) }
        }
    }

    override fun getMovieVideos(movieId: Long): Single<List<MovieVideosModel>> {
        return service.getMovieVideos(movieId)
            .map { MovieVideosResultResponse.transformList(it.results) }
    }


}
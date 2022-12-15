package id.bts.devin.alfagift_test.domain.repositories

import androidx.paging.PagingData
import id.bts.devin.alfagift_test.data.utils.NetworkResult
import id.bts.devin.alfagift_test.domain.models.MovieDetailsModel
import id.bts.devin.alfagift_test.domain.models.MovieReviewsModel
import id.bts.devin.alfagift_test.domain.models.MovieVideosModel
import id.bts.devin.alfagift_test.domain.models.NowPlayingModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getNowPlayingMovies(): Flow<PagingData<NowPlayingModel>>
    suspend fun getMovieDetails(movieId: Long): Flow<NetworkResult<MovieDetailsModel>>
    fun getMovieReviews(movieId: Long): Observable<PagingData<MovieReviewsModel>>
    fun getMovieVideos(movieId: Long): Single<List<MovieVideosModel>>
}
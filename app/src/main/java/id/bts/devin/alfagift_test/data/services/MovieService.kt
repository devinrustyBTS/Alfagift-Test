package id.bts.devin.alfagift_test.data.services

import id.bts.devin.alfagift_test.data.models.MovieDetailsResponse
import id.bts.devin.alfagift_test.data.models.MovieReviewResponse
import id.bts.devin.alfagift_test.data.models.MovieVideosResponse
import id.bts.devin.alfagift_test.data.models.NowPlayingResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): Response<NowPlayingResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Long
    ): Response<MovieDetailsResponse>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") movieId: Long,
        @Query("page") page: Int
    ): Single<MovieReviewResponse>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(
        @Path("movie_id") movieId: Long
    ): Single<MovieVideosResponse>

}

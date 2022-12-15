package id.bts.devin.alfagift_test.data.pagingsource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import id.bts.devin.alfagift_test.data.models.MovieReviewResponse
import id.bts.devin.alfagift_test.data.models.MovieReviewResultResponse
import id.bts.devin.alfagift_test.data.services.MovieService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class MovieReviewsPagingSource @Inject constructor(
    private val service: MovieService,
    private val movieId: Long
) : RxPagingSource<Int, MovieReviewResultResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieReviewResultResponse>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieReviewResultResponse>> {
        val pageIndex = params.key ?: 1
        return service.getMovieReviews(movieId, pageIndex)
            .map { toLoadResult(it, pageIndex) }
            .onErrorReturn { (LoadResult.Error(it)) }
    }

    private fun toLoadResult(
        response: MovieReviewResponse,
        position: Int
    ): LoadResult<Int, MovieReviewResultResponse> {
        if (response.results?.isNotEmpty() == true) return LoadResult.Page(
            data = response.results,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == response.totalPages?.toInt()) null else position + 1
        )
        return LoadResult.Error(Throwable())
    }

}
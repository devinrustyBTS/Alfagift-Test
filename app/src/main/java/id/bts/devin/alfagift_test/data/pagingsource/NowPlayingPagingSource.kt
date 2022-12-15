package id.bts.devin.alfagift_test.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.bts.devin.alfagift_test.data.models.NowPlayingResultResponse
import id.bts.devin.alfagift_test.data.services.MovieService
import id.bts.devin.alfagift_test.domain.models.NowPlayingModel
import retrofit2.HttpException
import javax.inject.Inject

class NowPlayingPagingSource @Inject constructor(
    private val service: MovieService
) : PagingSource<Int, NowPlayingModel>() {

    override fun getRefreshKey(state: PagingState<Int, NowPlayingModel>): Int? {
        return state.anchorPosition?.let { anchorPos ->
            state.closestPageToPosition(anchorPos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPos)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingModel> {
        val pageIndex = params.key ?: 1
        return try {
            val response = service.getNowPlayingMovies(pageIndex)
            response.body()?.let { responseBody ->
                val totalPages = responseBody.totalPages?.toInt() ?: 1
                val data = arrayListOf<NowPlayingModel>().apply {
                    responseBody.results?.forEach {
                        add(NowPlayingResultResponse.transform(it))
                    }
                }
                toLoadResult(data,totalPages, pageIndex)
            } ?: kotlin.run { throw HttpException(response) }
        } catch(e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun toLoadResult(
        data: List<NowPlayingModel>,
        totalPages: Int,
        position: Int
    ): LoadResult<Int, NowPlayingModel> {
       return LoadResult.Page(
            data = data,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == totalPages) null else position + 1
        )
    }

}
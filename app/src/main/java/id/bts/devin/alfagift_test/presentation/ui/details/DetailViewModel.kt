package id.bts.devin.alfagift_test.presentation.ui.details

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.bts.devin.alfagift_test.domain.models.MovieDetailsModel
import id.bts.devin.alfagift_test.domain.models.MovieReviewsModel
import id.bts.devin.alfagift_test.domain.models.MovieVideosModel
import id.bts.devin.alfagift_test.domain.usecases.MovieUseCase
import id.bts.devin.alfagift_test.presentation.base.BaseViewModel
import id.bts.devin.alfagift_test.presentation.utils.LiveResource
import id.bts.devin.alfagift_test.presentation.utils.Resource
import id.bts.devin.alfagift_test.presentation.utils.StateResource
import id.bts.devin.alfagift_test.presentation.utils.applySchedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : BaseViewModel() {

    val movieDetails = StateResource<MovieDetailsModel>()
    val movieVideos = LiveResource<List<MovieVideosModel>>()
    val userReviews = LiveResource<PagingData<MovieReviewsModel>>()

    suspend fun getMovieDetails(movieId: Long) {
        movieUseCase.getMovieDetails(movieId)
            .onEach { movieDetails.postValue(it) }
            .launchIn(viewModelScope)
    }

    fun getMovieVideos(movieId: Long) {
        val disposable = movieUseCase.getMovieVideos(movieId)
            .applySchedulers()
            .doOnSubscribe { movieVideos.postValue(Resource.loading()) }
            .subscribe({
                movieVideos.postValue(Resource.success(it))
            }, {
                movieVideos.postValue(Resource.error(it))
                it.printStackTrace()
            })
        collect(disposable)
    }

    fun getUserReviews(movieId: Long) {
        val disposable = movieUseCase.getMovieReviews(movieId)
            .cachedIn(viewModelScope)
            .applySchedulers()
            .doOnSubscribe { userReviews.postValue(Resource.loading()) }
            .subscribe({
                userReviews.postValue(Resource.success(it))
            }, {
                userReviews.postValue(Resource.error(it))
                it.printStackTrace()
            })
        collect(disposable)
    }
}
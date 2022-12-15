package id.bts.devin.alfagift_test.presentation.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.bts.devin.alfagift_test.domain.models.NowPlayingModel
import id.bts.devin.alfagift_test.domain.usecases.MovieUseCase
import id.bts.devin.alfagift_test.presentation.base.BaseViewModel
import id.bts.devin.alfagift_test.presentation.utils.StateBasic
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: MovieUseCase
): BaseViewModel() {

    val movies = StateBasic<PagingData<NowPlayingModel>>()

    suspend fun getMovies() {
        useCase.getNowPlayingMovies()
            .cachedIn(viewModelScope)
            .onEach { movies.postValue(it) }
            .launchIn(viewModelScope)
    }

}
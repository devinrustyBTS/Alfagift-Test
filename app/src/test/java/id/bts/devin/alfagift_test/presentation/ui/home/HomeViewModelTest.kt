package id.bts.devin.alfagift_test.presentation.ui.home

import androidx.paging.PagingData
import com.google.common.truth.Truth
import id.bts.devin.alfagift_test.TestDispatcherRule
import id.bts.devin.alfagift_test.domain.models.NowPlayingModel
import id.bts.devin.alfagift_test.domain.usecases.MovieUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    var testSchedulerRule = TestDispatcherRule()

    @Mock
    private lateinit var useCase: MovieUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun test_getMovies(): Unit = runTest {
        val data = listOf(Mockito.mock(NowPlayingModel::class.java))
        val pagingData = PagingData.from(data)
        val flowData: Flow<PagingData<NowPlayingModel>> = flow { emit(pagingData) }

        Mockito.`when`(useCase.getNowPlayingMovies())
            .thenReturn(flowData)

        val job = launch {
            viewModel.getMovies()
            viewModel.movies.collect {
                Truth.assertThat(it).isNotNull()
                Truth.assertThat(it).isInstanceOf(PagingData::class.java)
            }

            Mockito.verify(useCase).getNowPlayingMovies()
        }
        job.cancel()
    }
}
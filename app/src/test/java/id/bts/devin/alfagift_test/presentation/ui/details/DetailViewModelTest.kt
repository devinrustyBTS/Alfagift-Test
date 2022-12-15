package id.bts.devin.alfagift_test.presentation.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.google.common.truth.Truth
import id.bts.devin.alfagift_test.MainCoroutineRule
import id.bts.devin.alfagift_test.RxImmediateSchedulerRule
import id.bts.devin.alfagift_test.TestDispatcherRule
import id.bts.devin.alfagift_test.data.utils.NetworkResult
import id.bts.devin.alfagift_test.domain.models.MovieDetailsModel
import id.bts.devin.alfagift_test.domain.models.MovieReviewsModel
import id.bts.devin.alfagift_test.domain.models.MovieVideosModel
import id.bts.devin.alfagift_test.domain.usecases.MovieUseCase
import id.bts.devin.alfagift_test.getOrAwaitValueTest
import id.bts.devin.alfagift_test.presentation.utils.Resource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var useCase: MovieUseCase

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = DetailViewModel(useCase)
    }

    @Test
    fun test_getMovieDetails() = runTest {
        val data = Mockito.mock(MovieDetailsModel::class.java)
        val flowData = flow { emit(NetworkResult.Success(data)) }

        Mockito.`when`(useCase.getMovieDetails(1))
            .thenReturn(flowData)

        val job = launch {
            viewModel.getMovieDetails(1)
            viewModel.movieDetails.collect(
                onSuccess = {
                    Truth.assertThat(it).isNotNull()
                    Truth.assertThat(it).isInstanceOf(MovieDetailsModel::class.java)
                }
            )

            Mockito.verify(useCase).getMovieDetails(1)
        }
        job.cancel()
    }

    @Test
    fun test_getMovieReviews() = runTest {
        val data = listOf(Mockito.mock(MovieReviewsModel::class.java))
        val expected = PagingData.from(data)
        Mockito.`when`(useCase.getMovieReviews(1))
            .thenReturn(Observable.just(expected))

        viewModel.getUserReviews(1)
        viewModel.userReviews.getOrAwaitValueTest {
            Truth.assertThat(it.data).isNotNull()
            Truth.assertThat(it.data).isInstanceOf(PagingData::class.java)
            Truth.assertThat(it.status).isEqualTo(Resource.Status.SUCCESS)
        }
        Mockito.verify(useCase).getMovieReviews(1)
    }

    @Test
    fun test_getMovieReviews_Failed() = runTest {
        Mockito.`when`(useCase.getMovieReviews(1))
            .thenReturn(Observable.error(Throwable()))

        viewModel.getUserReviews(1)
        viewModel.userReviews.getOrAwaitValueTest {
            Truth.assertThat(it.data).isNull()
        }
        Mockito.verify(useCase).getMovieReviews(1)
    }

    @Test
    fun test_getMovieVideos() = runTest {
        val data = listOf(Mockito.mock(MovieVideosModel::class.java))

        Mockito.`when`(useCase.getMovieVideos(1))
            .thenReturn(Single.just(data))

        viewModel.getMovieVideos(1)
        viewModel.movieVideos.getOrAwaitValueTest {
            Truth.assertThat(it.data).isNotNull()
            Truth.assertThat(it.data).isInstanceOf(List::class.java)
            Truth.assertThat(it.status).isEqualTo(Resource.Status.SUCCESS)
        }

        Mockito.verify(useCase).getMovieVideos(1)
    }

}
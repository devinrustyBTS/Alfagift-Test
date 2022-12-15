package id.bts.devin.alfagift_test.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.google.common.truth.Truth
import id.bts.devin.alfagift_test.RxImmediateSchedulerRule
import id.bts.devin.alfagift_test.data.utils.NetworkResult
import id.bts.devin.alfagift_test.domain.models.MovieDetailsModel
import id.bts.devin.alfagift_test.domain.models.MovieReviewsModel
import id.bts.devin.alfagift_test.domain.models.MovieVideosModel
import id.bts.devin.alfagift_test.domain.models.NowPlayingModel
import id.bts.devin.alfagift_test.domain.repositories.MovieRepository
import id.bts.devin.alfagift_test.getOrAwaitValueTest
import id.bts.devin.alfagift_test.presentation.utils.Resource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

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
class MovieUseCaseImplTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    private lateinit var useCase: MovieUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCase = MovieUseCaseImpl(repository)
    }

    @Test
    fun test_getMovies() = runTest {
        val data = listOf(Mockito.mock(NowPlayingModel::class.java))
        val pagingData = PagingData.from(data)
        val flowData: Flow<PagingData<NowPlayingModel>> = flow { emit(pagingData) }

        Mockito.`when`(repository.getNowPlayingMovies())
            .thenReturn(flowData)

        val job = launch {
            useCase.getNowPlayingMovies()
            useCase.getNowPlayingMovies().collect {
                Truth.assertThat(it).isNotNull()
                Truth.assertThat(it).isInstanceOf(PagingData::class.java)
            }

            Mockito.verify(repository).getNowPlayingMovies()
        }
        job.cancel()
    }

    @Test
    fun test_getMovieDetails() = runTest {
        val data = Mockito.mock(MovieDetailsModel::class.java)
        val flowData = flow { emit(NetworkResult.Success(data)) }

        Mockito.`when`(repository.getMovieDetails(1))
            .thenReturn(flowData)

        val job = launch {
            useCase.getMovieDetails(1).collectLatest {
                Truth.assertThat(it).isNotNull()
                Truth.assertThat(it).isInstanceOf(NetworkResult::class.java)
            }

            Mockito.verify(repository).getMovieDetails(1)
        }
        job.cancel()
    }

    @Test
    fun test_getMovieReviews() = runTest {
        val data = listOf(Mockito.mock(MovieReviewsModel::class.java))
        val expected = PagingData.from(data)
        Mockito.`when`(repository.getMovieReviews(1))
            .thenReturn(Observable.just(expected))

        val result = useCase.getMovieReviews(1).test()
        result.onSuccess(expected)
        Truth.assertThat(result.hasSubscription()).isTrue()
        Truth.assertThat(result.values()).isNotNull()

        Mockito.verify(repository).getMovieReviews(1)
    }

    @Test
    fun test_getMovieVideos() = runTest {
        val data = listOf(Mockito.mock(MovieVideosModel::class.java))

        Mockito.`when`(repository.getMovieVideos(1))
            .thenReturn(Single.just(data))

        val result = useCase.getMovieVideos(1).test()
        result.onSuccess(data)
        Truth.assertThat(result.hasSubscription()).isTrue()
        Truth.assertThat(result.values()).isNotNull()

        Mockito.verify(repository).getMovieVideos(1)
    }
}
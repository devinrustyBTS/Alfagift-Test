package id.bts.devin.alfagift_test.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.google.common.truth.Truth
import id.bts.devin.alfagift_test.RxImmediateSchedulerRule
import id.bts.devin.alfagift_test.data.models.MovieDetailsResponse
import id.bts.devin.alfagift_test.data.models.MovieReviewResponse
import id.bts.devin.alfagift_test.data.models.MovieVideosResponse
import id.bts.devin.alfagift_test.data.models.NowPlayingResponse
import id.bts.devin.alfagift_test.data.services.MovieService
import id.bts.devin.alfagift_test.data.utils.NetworkResult
import id.bts.devin.alfagift_test.domain.models.MovieReviewsModel
import id.bts.devin.alfagift_test.domain.models.MovieVideosModel
import id.bts.devin.alfagift_test.domain.repositories.MovieRepository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
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
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryImplTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var service: MovieService
    lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = MovieRepositoryImpl(service)
    }

    @Test
    fun test_getMovies() = runTest {
        val response = Mockito.mock(NowPlayingResponse::class.java)

        Mockito.`when`(service.getNowPlayingMovies(1))
            .thenReturn(Response.success(response))

        val job = launch {
            repository.getNowPlayingMovies()
            repository.getNowPlayingMovies().collect {
                Truth.assertThat(it).isNotNull()
                Truth.assertThat(it).isInstanceOf(PagingData::class.java)
            }

            Mockito.verify(service).getNowPlayingMovies(1)
        }
        job.cancel()
    }

    @Test
    fun test_getMovieDetails() = runTest {
        val response = Mockito.mock(MovieDetailsResponse::class.java)

        Mockito.`when`(service.getMovieDetails(1))
            .thenReturn(Response.success(response))

        val job = launch {
            repository.getMovieDetails(1).collectLatest {
                Truth.assertThat(it).isNotNull()
                Truth.assertThat(it).isInstanceOf(NetworkResult::class.java)
            }

            Mockito.verify(service).getMovieDetails(1)
        }
        job.cancel()
    }

    @Test
    fun test_getMovieReviews() = runTest {
        //val response = Mockito.mock(MovieReviewResponse::class.java)
        val dataModel = listOf(Mockito.mock(MovieReviewsModel::class.java))
        /*Mockito.`when`(service.getMovieReviews(1, 1))
            .thenReturn(Single.just(response))*/

        val result = repository.getMovieReviews(1).test()
        result.onSuccess(PagingData.from(dataModel))
        Truth.assertThat(result.hasSubscription()).isTrue()
        Truth.assertThat(result.values()).isNotNull()
        Truth.assertThat(result.values()).isNotEmpty()

        //Mockito.verify(service).getMovieReviews(1, 1)
    }

    @Test
    fun test_getMovieVideos() = runTest {
        val response = Mockito.mock(MovieVideosResponse::class.java)
        val dataModel = listOf(Mockito.mock(MovieVideosModel::class.java))
        Mockito.`when`(service.getMovieVideos(1))
            .thenReturn(Single.just(response))

        val result = repository.getMovieVideos(1).test()
        result.onSuccess(dataModel)
        Truth.assertThat(result.hasSubscription()).isTrue()
        Truth.assertThat(result.values()).isNotNull()

        Mockito.verify(service).getMovieVideos(1)
    }
}
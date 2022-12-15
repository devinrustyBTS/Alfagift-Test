package id.bts.devin.alfagift_test.presentation.ui.details

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.bts.devin.alfagift_test.R
import id.bts.devin.alfagift_test.databinding.FragmentDetailBinding
import id.bts.devin.alfagift_test.domain.models.MovieDetailsModel
import id.bts.devin.alfagift_test.presentation.base.BaseFragment
import id.bts.devin.alfagift_test.presentation.ui.details.adapters.DetailViewPagerAdapter
import id.bts.devin.alfagift_test.presentation.utils.getYear
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel: DetailViewModel by viewModels()
    private val navArgs: DetailFragmentArgs by navArgs()
    private lateinit var viewPagerAdapter: DetailViewPagerAdapter

    override fun setLayout(inflater: LayoutInflater): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater)
    }

    override fun onFragmentCreated() {
        setupObserver()
        loadData()
    }

    private fun setupObserver() {
        viewModel.movieDetails.collectData {
            setupView(it)
            setupViewPager(it)
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getMovieDetails(navArgs.movieId)
            }
        }
    }

    private fun setupView(detail: MovieDetailsModel) {
        binding.apply {
            sivDetailBackdrop.load(detail.backdropPath)
            sivDetailPoster.load(detail.posterPath)
            tvMovieTitle.text = detail.title
            movieInformationView.apply {
                tvMovieRuntime.text = getString(R.string.movie_runtime, detail.runtime)
                tvReleaseYear.text = detail.releaseDate.getYear()
                tvMovieGenres.text = detail.genres.first().name
            }
        }
    }

    private fun setupViewPager(data: MovieDetailsModel) {
        viewPagerAdapter = DetailViewPagerAdapter(childFragmentManager, lifecycle, data)
        binding.apply {
            viewPagerDetail.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayoutDetails, viewPagerDetail) { tab, position ->
                when (position) {
                    0 -> tab.setText(R.string.about_movie)
                    1 -> tab.setText(R.string.common_reviews)
                    2 -> tab.setText(R.string.common_videos)
                }
            }.attach()
        }
    }

}
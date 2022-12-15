package id.bts.devin.alfagift_test.presentation.ui.details.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.bts.devin.alfagift_test.R
import id.bts.devin.alfagift_test.databinding.FragmentDetailVideosBinding
import id.bts.devin.alfagift_test.domain.models.MovieDetailsModel
import id.bts.devin.alfagift_test.domain.models.MovieVideosModel
import id.bts.devin.alfagift_test.presentation.base.BaseFragment
import id.bts.devin.alfagift_test.presentation.ui.details.DetailViewModel
import id.bts.devin.alfagift_test.presentation.ui.details.adapters.DetailVideoAdapter

@AndroidEntryPoint
class DetailVideosFragment : BaseFragment<FragmentDetailVideosBinding>() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var videoAdapter: DetailVideoAdapter

    override fun setLayout(inflater: LayoutInflater): FragmentDetailVideosBinding {
        return FragmentDetailVideosBinding.inflate(inflater)
    }

    override fun onFragmentCreated() {
        loadData()
        setupObserver()
        setupRecyclerView()
    }

    private fun loadData() {
        val movieId = arguments?.getLong(MOVIE_ID) ?: 0L
        videoAdapter = DetailVideoAdapter(lifecycle)
        viewModel.getMovieVideos(movieId)
        setupButtonListener(movieId)
    }

    private fun setupObserver() {
        viewModel.movieVideos.observeData { setupView(it) }
    }

    private fun setupRecyclerView() {
        binding.rvMovieVideos.adapter = videoAdapter
    }

    private fun setupView(data: List<MovieVideosModel>) {
        if (data.isEmpty()) {
            binding.apply {
                rvMovieVideos.isVisible = false
                videoEmptyLayout.isVisible = true
            }
        }
        videoAdapter.submitList(data)
    }

    private fun setupButtonListener(movieId: Long) {
        binding.viewVideosEmpty.btnEmptyRefresh.setOnClickListener {
            viewModel.getMovieVideos(movieId)
        }
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movieId: Long) =
            DetailVideosFragment().apply {
                arguments = Bundle().apply {
                    putLong(MOVIE_ID, movieId)
                }
            }
    }
}
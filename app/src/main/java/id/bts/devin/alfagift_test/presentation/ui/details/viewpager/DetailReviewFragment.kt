package id.bts.devin.alfagift_test.presentation.ui.details.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import id.bts.devin.alfagift_test.databinding.FragmentDetailReviewBinding
import id.bts.devin.alfagift_test.presentation.base.BaseFragment
import id.bts.devin.alfagift_test.presentation.ui.details.DetailViewModel
import id.bts.devin.alfagift_test.presentation.ui.details.adapters.DetailReviewAdapter

@AndroidEntryPoint
class DetailReviewFragment : BaseFragment<FragmentDetailReviewBinding>() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var detailReviewAdapter: DetailReviewAdapter

    override fun setLayout(inflater: LayoutInflater): FragmentDetailReviewBinding {
        return FragmentDetailReviewBinding.inflate(inflater)
    }

    override fun onFragmentCreated() {
        val movieId = arguments?.getLong(MOVIE_ID) ?: 0L
        setupObserver()
        setupAdapter()
        setupRecyclerView()
        setupButtonListener(movieId)
        viewModel.getUserReviews(movieId)
    }

    private fun setupObserver() {
        viewModel.userReviews.observeData {
            detailReviewAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvMovieReviews.adapter = detailReviewAdapter
    }

    private fun setupAdapter(){
        detailReviewAdapter = DetailReviewAdapter().apply {
            addLoadStateListener { loadState ->
                when(loadState.refresh) {
                    is LoadState.Loading -> showLoadingDialog()
                    is LoadState.Error -> showEmptyView()
                    is LoadState.NotLoading -> {
                        if (itemCount < 1) showEmptyView()
                        else hideLoadingDialog()
                    }
                }
            }
        }
    }

    private fun setupButtonListener(movieId: Long) {
        binding.viewReviewEmpty.btnEmptyRefresh.setOnClickListener {
            viewModel.getUserReviews(movieId)
        }
    }

    private fun showEmptyView() {
        binding.rvMovieReviews.isVisible = false
        binding.homeEmptyLayout.isVisible = true
        hideLoadingDialog()
    }

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movieId: Long) = DetailReviewFragment().apply {
            arguments = Bundle().apply {
                putLong(MOVIE_ID, movieId)
            }
        }
    }
}
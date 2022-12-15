package id.bts.devin.alfagift_test.presentation.ui.home

import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.bts.devin.alfagift_test.databinding.FragmentHomeBinding
import id.bts.devin.alfagift_test.domain.models.NowPlayingModel
import id.bts.devin.alfagift_test.presentation.base.BaseFragment
import id.bts.devin.alfagift_test.presentation.utils.showSnackBar
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun setLayout(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun onFragmentCreated() {
        setupAdapter()
        setupRecyclerView()
        setupButtonListener()
        collectMovies()
        fetchMovies()
    }

    private fun fetchMovies() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getMovies()
            }
        }
    }

    private fun collectMovies() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.movies.collect { homeAdapter.submitData(lifecycle, it) }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvNowPlaying.apply {
            adapter = homeAdapter
            layoutManager = GridLayoutManager(this.context, 3)
        }
    }

    private fun setupAdapter() {
        homeAdapter = HomeAdapter().apply {
            setOnItemClickListener { navigateToDetail(it) }
            addLoadStateListener { loadState ->
                when(loadState.refresh) {
                    is LoadState.Error -> showEmptyView()
                    is LoadState.Loading -> showLoadingDialog()
                    is LoadState.NotLoading -> {
                        binding.rvNowPlaying.isVisible = itemCount > 0
                        binding.homeEmptyLayout.isVisible = itemCount < 0
                        hideLoadingDialog()
                    }
                }
            }
        }
    }

    private fun setupButtonListener() {
        binding.viewHomeEmpty.btnEmptyRefresh.setOnClickListener {
            showLoadingDialog()
            fetchMovies()
        }
    }

    private fun showEmptyView() {
        binding.rvNowPlaying.isVisible = false
        binding.homeEmptyLayout.isVisible = true
        hideLoadingDialog()
    }

    private fun navigateToDetail(data: NowPlayingModel) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment().apply {
            movieId = data.id
            movieName = data.title
        }
        findNavController().navigate(action)
    }

}
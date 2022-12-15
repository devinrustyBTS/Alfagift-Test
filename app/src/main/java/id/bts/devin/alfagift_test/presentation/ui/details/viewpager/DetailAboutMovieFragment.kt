package id.bts.devin.alfagift_test.presentation.ui.details.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import id.bts.devin.alfagift_test.databinding.FragmentDetailAboutMovieBinding
import id.bts.devin.alfagift_test.presentation.base.BaseFragment

@AndroidEntryPoint
class DetailAboutMovieFragment : BaseFragment<FragmentDetailAboutMovieBinding>() {

    override fun setLayout(inflater: LayoutInflater): FragmentDetailAboutMovieBinding {
        return FragmentDetailAboutMovieBinding.inflate(inflater)
    }

    override fun onFragmentCreated() {
        val overview = arguments?.getString(MOVIE_OVERVIEW) ?: ""
        setupView(overview)
    }

    private fun setupView(content: String) {
        binding.tvMovieAbout.text = content
    }

    companion object {
        private const val MOVIE_OVERVIEW = "movie_overview"

        fun newInstance(overview: String) = DetailAboutMovieFragment().apply {
            arguments = Bundle().apply {
                putString(MOVIE_OVERVIEW, overview)
            }
        }
    }

}
package id.bts.devin.alfagift_test.presentation.ui.details.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.bts.devin.alfagift_test.domain.models.MovieDetailsModel
import id.bts.devin.alfagift_test.presentation.ui.details.viewpager.DetailAboutMovieFragment
import id.bts.devin.alfagift_test.presentation.ui.details.viewpager.DetailReviewFragment
import id.bts.devin.alfagift_test.presentation.ui.details.viewpager.DetailVideosFragment

class DetailViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val data: MovieDetailsModel
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = TABS_COUNT

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DetailAboutMovieFragment.newInstance(data.overview)
            1 -> DetailReviewFragment.newInstance(data.id)
            2 -> DetailVideosFragment.newInstance(data.id)
            else -> throw IllegalStateException("Invalid position $position")
        }
    }

    companion object {
        private const val TABS_COUNT = 3
    }
}
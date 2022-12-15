package id.bts.devin.alfagift_test.presentation.ui.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.bts.devin.alfagift_test.databinding.ItemMovieReviewBinding
import id.bts.devin.alfagift_test.domain.models.MovieReviewsModel
import id.bts.devin.alfagift_test.presentation.utils.loadAvatar

class DetailReviewAdapter
    : PagingDataAdapter<MovieReviewsModel, DetailReviewAdapter.DetailReviewViewHolder>(COMPARATOR) {

    override fun onBindViewHolder(holder: DetailReviewViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailReviewViewHolder {
        return DetailReviewViewHolder(
            ItemMovieReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class DetailReviewViewHolder(
        private val binding: ItemMovieReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieReviewsModel) {
            binding.apply {
                tvUserName.text = data.author
                tvUserReview.text = data.content
                tvUserScore.text = if (data.authorDetails.rating == 0) "-"
                else data.authorDetails.rating.toString()
                sivUserAvatar.loadAvatar(data.authorDetails.avatarPath)
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<MovieReviewsModel>() {
            override fun areItemsTheSame(
                oldItem: MovieReviewsModel,
                newItem: MovieReviewsModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieReviewsModel,
                newItem: MovieReviewsModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}
package id.bts.devin.alfagift_test.presentation.ui.details.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import id.bts.devin.alfagift_test.databinding.ItemMovieVideosBinding
import id.bts.devin.alfagift_test.domain.models.MovieVideosModel

class DetailVideoAdapter(private val lifecycle: Lifecycle)
    : ListAdapter<MovieVideosModel, DetailVideoAdapter.DetailVideoViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailVideoViewHolder {
        return DetailVideoViewHolder(
            ItemMovieVideosBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DetailVideoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DetailVideoViewHolder(
        private val binding: ItemMovieVideosBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieVideosModel) {
            lifecycle.addObserver(binding.movieVideoPlayer)
            binding.movieVideoPlayer.addYouTubePlayerListener( object: AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(data.key, 0F)
                }
            })
        }
    }

    companion object {
        val COMPARATOR = object: DiffUtil.ItemCallback<MovieVideosModel>() {
            override fun areItemsTheSame(
                oldItem: MovieVideosModel,
                newItem: MovieVideosModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieVideosModel,
                newItem: MovieVideosModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
package id.bts.devin.alfagift_test.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.bts.devin.alfagift_test.databinding.ItemNowPlayingBinding
import id.bts.devin.alfagift_test.domain.models.NowPlayingModel

class HomeAdapter: PagingDataAdapter<NowPlayingModel, HomeAdapter.HomeViewHolder>(COMPARATOR) {

    private var onItemClickListener: ((data: NowPlayingModel) -> Unit)? = null

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemNowPlayingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setOnItemClickListener(listener: ((data: NowPlayingModel) -> Unit)) {
        this.onItemClickListener = listener
    }

    inner class HomeViewHolder(
        private val binding: ItemNowPlayingBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NowPlayingModel) {
            binding.ivMoviePoster.apply {
                load(data.posterPath)
                setOnClickListener { onItemClickListener?.invoke(data) }
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<NowPlayingModel>() {
            override fun areItemsTheSame(
                oldItem: NowPlayingModel,
                newItem: NowPlayingModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: NowPlayingModel,
                newItem: NowPlayingModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
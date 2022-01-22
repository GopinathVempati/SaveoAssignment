package com.saveo.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saveo.R
import com.saveo.databinding.ItemMovieBinding
import com.saveo.model.ResultsItem
import com.saveo.utils.checkNull
import com.saveo.utils.loadImageFromUrl

class MoviesAdapter(val itemClickListener: ItemClickListener) :
    PagedListAdapter<ResultsItem, RecyclerView.ViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ResultsItem>() {
            override fun areItemsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean =
                oldItem.id?.equals(newItem.id) == true

            override fun areContentsTheSame(
                oldItem: ResultsItem,
                newItem: ResultsItem
            ): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemMovieBinding: ItemMovieBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie, parent, false
        )
        return MovieVH(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mHolder = holder as MovieVH
        getItem(position)?.let { mHolder.bind(it,itemClickListener) }
    }

    class MovieVH(itemMovieBinding: ItemMovieBinding) :
        RecyclerView.ViewHolder(itemMovieBinding.root) {
        val binding = itemMovieBinding
        fun bind(resultsItem: ResultsItem, itemClickListener: ItemClickListener) {
            binding.movieImageView.loadImageFromUrl(resultsItem.posterPath)
            itemView.setOnClickListener {
                itemClickListener.onItemClick(resultsItem)
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(resultsItem: ResultsItem)
    }
}
package com.sercanorhangazi.githubsearch.features.gridLayout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.size
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sercanorhangazi.githubsearch.databinding.CellGridLayoutBinding

class GridLayoutAdapter: ListAdapter<Int, GridLayoutAdapter.GridImageViewHolder>(GridImageComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridImageViewHolder {
        return GridImageViewHolder(
            CellGridLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GridImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GridImageViewHolder(val binding: CellGridLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            binding.gridImageView.setImageResource(item)
        }
    }

    class GridImageComparator: DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
    }



}
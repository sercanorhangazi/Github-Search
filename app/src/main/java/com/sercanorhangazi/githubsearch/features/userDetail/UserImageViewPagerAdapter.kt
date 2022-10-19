package com.sercanorhangazi.githubsearch.features.userDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sercanorhangazi.githubsearch.databinding.ImageViewPagerItemBinding

class UserImageViewPagerAdapter : ListAdapter<String, UserImageViewPagerAdapter.UserImageViewHolder>(
    UserImageComparator()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserImageViewHolder {
        val binding = ImageViewPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserImageViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class UserImageViewHolder(private val binding: ImageViewPagerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            Glide.with(binding.root.context).load(url).into(binding.imageView)
        }
    }

    class UserImageComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }

}
package com.sercanorhangazi.githubsearch.features.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sercanorhangazi.githubsearch.R
import com.sercanorhangazi.githubsearch.data.SearchUser
import com.sercanorhangazi.githubsearch.databinding.UserSearchCellBinding

class SearchUserPagingAdapter(
    private val onItemClick: (SearchUser) -> Unit
): PagingDataAdapter<SearchUser, SearchUserPagingAdapter.SearchUserViewHolder>(SearchUserComparator())  {

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        val binding = UserSearchCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchUserViewHolder(binding,
            onItemClick = { position ->
                val user = getItem(position)
                if (user != null) {
                    onItemClick(user)
                }
            })
    }

    inner class SearchUserViewHolder(private val binding: UserSearchCellBinding,
                                     private val onItemClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: SearchUser) {
            binding.apply {
                tvUsername.text = user.username
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .error(R.mipmap.ic_launcher)
                    .into(ivAvatar)
            }
        }

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClick(position)
                    }
                }
            }
        }
    }

    class SearchUserComparator: DiffUtil.ItemCallback<SearchUser>() {
        override fun areItemsTheSame(oldItem: SearchUser, newItem: SearchUser) = oldItem == newItem
        override fun areContentsTheSame(oldItem: SearchUser, newItem: SearchUser) = oldItem.username == newItem.username
    }
}
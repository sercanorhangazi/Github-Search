package com.sercanorhangazi.githubsearch.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sercanorhangazi.githubsearch.databinding.UserSearchCellBinding
import com.sercanorhangazi.githubsearch.model.User

class UserSearchCellAdapter(
    private val items: ArrayList<User>,
    private val clickListener: (username: String) ->  Unit) : RecyclerView.Adapter<UserSearchCellAdapter.UserSearchCell>() {

    private var currentQuery = ""
    private var allItems = ArrayList<User>()

    init {
        allItems = items
    }

    fun setItems(items: ArrayList<User>, query: String) {
        Log.d("DEBUG", "setItems: $currentQuery , $query")
        if (allItems.size >= items.size && query == currentQuery) {
            Log.d("DEBUG", "${allItems.size} <= ${items.size} Append items")
            addItems(items)
        } else {
            currentQuery = query
            Log.d("DEBUG", "${allItems.size} <= ${items.size} Set items")
            allItems = items
            notifyDataSetChanged()
        }
    }

    fun addItems(items: ArrayList<User>) {
        allItems.addAll(items)
        notifyDataSetChanged()
    }

    fun clearAllItems() {
        allItems.clear()
    }

    class UserSearchCell(val binding: UserSearchCellBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(user: User, pos: Int) {

            binding.apply {
                Glide.with(this.root).load(user.avatar_url).into(ivAvatar)

                val message = user.login
                binding.tvUsername.text =  message
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : UserSearchCell {
        val binding = UserSearchCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserSearchCell(binding)
    }

    override fun onBindViewHolder(holder: UserSearchCell, position: Int) {
        val user = allItems[position]
        holder.bindItem(user, position)

        holder.binding.llMain.setOnClickListener {
            clickListener.invoke(allItems[position].login)
        }
    }

    override fun getItemCount(): Int {
        return allItems.size
    }

}
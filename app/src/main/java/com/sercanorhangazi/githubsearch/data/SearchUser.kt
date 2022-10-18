package com.sercanorhangazi.githubsearch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_user")
data class SearchUser(
    @PrimaryKey(autoGenerate = false) val username: String,
    val avatarUrl: String,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "search_results", primaryKeys = ["searchQuery", "username"])
data class SearchResult(
    val searchQuery: String,
    val username: String,
    val queryPosition: Int
)
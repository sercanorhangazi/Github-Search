package com.sercanorhangazi.githubsearch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_query_remote_keys")
class SearchQueryRemoteKey(
    @PrimaryKey val searchQuery: String,
    val nextPageKey: Int
)
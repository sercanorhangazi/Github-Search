package com.sercanorhangazi.githubsearch.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import javax.annotation.Nullable

@Entity(tableName = "search-result")
data class UserSearchResultModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var query: String,
    var page: Int,
    var incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int = 0
)
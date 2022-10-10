package com.sercanorhangazi.githubsearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val login: String,
    val avatar_url: String
)
package com.sercanorhangazi.githubsearch.model

data class UserSearchResultModel(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int,
    var query: String?,
    var page: Int
)
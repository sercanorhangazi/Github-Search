package com.sercanorhangazi.mvvmpractise.model

data class UserSearchResultModel(
    val incomplete_results: Boolean,
    val items: List<User>,
    val total_count: Int
)
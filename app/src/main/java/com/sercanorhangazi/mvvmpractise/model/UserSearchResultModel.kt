package com.sercanorhangazi.mvvmpractise.model

data class UserSearchResultModel(
    val incomplete_results: Boolean,
    val items: List<User>,
    var total_count: Int,
    var query: String?
)
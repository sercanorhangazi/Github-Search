package com.sercanorhangazi.mvvmpractise.searchUser.models

data class UserSearchResultModel(
    val incomplete_results: Boolean,
    val items: List<İtem>,
    val total_count: Int
)
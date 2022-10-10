package com.sercanorhangazi.githubsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sercanorhangazi.githubsearch.data.SearchRepository
import com.sercanorhangazi.githubsearch.model.UserSearchResultModel
import com.sercanorhangazi.githubsearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    var userSearchResults: LiveData<Resource<UserSearchResultModel>>? = null

    private var currentQuery = ""
    private var currentPage = 1

    fun getNextPage() {
        getUserSearchResult(currentQuery, currentPage + 1)
    }

    fun getUserSearchResult(query: String, page: Int = 1) {
        if (query == currentQuery && page == currentPage) { return }
        if (query != currentQuery) {
            currentQuery = query
            currentPage = 1
        }
        if (query == currentQuery && page != currentPage) {
            currentPage = page
        }
        Log.d("DEBUG", "getUserSearchResult: query is $query and the page is $page")
        userSearchResults = searchRepository.getSearchResults(query, page).asLiveData()
    }


}
package com.sercanorhangazi.githubsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercanorhangazi.githubsearch.retrofit.GithubApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import com.sercanorhangazi.githubsearch.model.UserSearchResultModel as UserSearchResultModel

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val api: GithubApi
): ViewModel() {

    private var userSearchLiveData = MutableLiveData<UserSearchResultModel>()
    var userSearchResults: LiveData<UserSearchResultModel> = userSearchLiveData

    private var currentQuery = ""
    private var currentPage = 1

    fun getNextPage() {
        getUserSearchResult(currentQuery, currentPage+1)
    }

    fun getUserSearchResult(query: String, page: Int = 1) {
        Log.d("DEBUG","current query = $currentQuery , current page : $currentPage, query: $query, page : $page")

        if (query == currentQuery && page == currentPage) { return }
        if (query != currentQuery) {
            currentQuery = query
            currentPage = 1
        }
        if (query == currentQuery && page != currentPage) {
            currentPage = page
        }

        Log.d("DEBUG"," - current query = $currentQuery , current page : $currentPage, query: $query, page : $page")

        viewModelScope.launch {
            val results = api.getUserSearchResults(query, page)
            results.query = currentQuery
            results.page = currentPage
            userSearchLiveData.value = results
        }

    }

}
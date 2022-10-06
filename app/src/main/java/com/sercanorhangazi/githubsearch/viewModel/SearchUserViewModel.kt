package com.sercanorhangazi.githubsearch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sercanorhangazi.githubsearch.retrofit.GithubApi
import com.sercanorhangazi.githubsearch.model.UserSearchResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val api: GithubApi
): ViewModel() {

    private var userSearchLiveData = MutableLiveData<UserSearchResultModel>()

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

        api.getUserSearchResults(query, page).enqueue(object: Callback<UserSearchResultModel> {
            override fun onResponse(
                call: Call<UserSearchResultModel>,
                response: Response<UserSearchResultModel>
            ) {
                Log.d("Request url", call.request().url().toString())
                response.body()?.let { result ->
                    result.query = currentQuery
                    userSearchLiveData.value = result
                }
            }

            override fun onFailure(call: Call<UserSearchResultModel>, t: Throwable) {
                Log.d("DEBUG", "Couldn't fetch user search result : ${t.message.toString()}")
            }
        })
    }

    fun observeUserSearchLiveData(): LiveData<UserSearchResultModel> {
        return userSearchLiveData
    }
}
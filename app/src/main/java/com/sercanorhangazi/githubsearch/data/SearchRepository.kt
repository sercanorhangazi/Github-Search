package com.sercanorhangazi.githubsearch.data

import android.util.Log
import androidx.room.withTransaction
import com.sercanorhangazi.githubsearch.retrofit.GithubApi
import com.sercanorhangazi.githubsearch.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: GithubApi,
    private val db: SearchDatabase
) {
    private val searchDao = db.searchDao()

    fun getSearchResults(searchQuery: String, page: Int) = networkBoundResource(
        query = {
            searchDao.getSearchResultsFor(searchQuery, page)
        },
        fetch = {
            delay(1000)
            Log.d("DEBUG", "------------------------------------------------")
            Log.d("DEBUG", "getSearchResults: $searchQuery, $page")
            Log.d("DEBUG", "------------------------------------------------")
            val searchResult = api.getUserSearchResults(searchQuery, page)
            searchResult.apply {
                this.query = searchQuery
                this.page = page
            }
            searchResult
        },
        saveFetchResult = { searchResult ->
            db.withTransaction {
                searchDao.deleteResultsFor(searchQuery, page)
                searchDao.insertSearchResult(searchResult)
            }
        }
    )
}
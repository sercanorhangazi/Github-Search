package com.sercanorhangazi.githubsearch.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sercanorhangazi.githubsearch.api.GithubApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val searchDatabase: SearchDatabase
) {
    private val searchDao = searchDatabase.searchDao()

    fun getSearchResultsPaged(query: String): Flow<PagingData<SearchUser>> =
        Pager(
            config = PagingConfig(pageSize = 10, maxSize = 200),
            remoteMediator = SearchNewsRemoteMediator(query, githubApi, searchDatabase),
            pagingSourceFactory = { searchDao.getSearchResultUsersPaged(query) }
        ).flow
}
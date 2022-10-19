package com.sercanorhangazi.githubsearch.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.withTransaction
import com.sercanorhangazi.githubsearch.api.GithubApi
import com.sercanorhangazi.githubsearch.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: GithubApi,
    private val searchDatabase: SearchDatabase
) {
    private val searchDao = searchDatabase.searchDao()

    fun getSearchResultsPaged(query: String): Flow<PagingData<SearchUser>> =
        Pager(
            config = PagingConfig(pageSize = 10, maxSize = 200),
            remoteMediator = SearchNewsRemoteMediator(query, api, searchDatabase),
            pagingSourceFactory = { searchDao.getSearchResultUsersPaged(query) }
        ).flow

    fun getUserDetails(username: String) = networkBoundResource(
        query = {
            searchDao.getUserDetails(username)
        },
        fetch = {
            delay(1000)
            api.fetchUserDetails(username)
        },
        saveFetchResult = { userDetailDto ->
            val user = UserDetail(
                username = userDetailDto.login,
                avatarUrl = userDetailDto.avatar_url,
                htmlUrl = userDetailDto.html_url,
                name = userDetailDto.name
            )
            searchDatabase.withTransaction {
                searchDao.deleteUserDetails(user.username)
                searchDao.insertUserDetail(user)
            }
        }
    )
}
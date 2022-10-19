package com.sercanorhangazi.githubsearch.data

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sercanorhangazi.githubsearch.api.GithubApi
import retrofit2.HttpException
import java.io.IOException

private const val NEWS_STARTING_PAGE_INDEX = 1

class SearchNewsRemoteMediator(
    private val searchQuery: String,
    private val githubApi: GithubApi,
    private val searchDatabase: SearchDatabase
) : RemoteMediator<Int, SearchUser>() {

    private val searchUserDao = searchDatabase.searchDao()
    private val searchQueryRemoteKeyDao = searchDatabase.searchQueryRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchUser>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> NEWS_STARTING_PAGE_INDEX
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> searchQueryRemoteKeyDao.getRemoteKey(searchQuery).nextPageKey
        }

        try {
            val response = githubApi.searchUser(searchQuery, page, state.config.pageSize)
            val serverSearchResults = response.items

            //val bookmarkedArticles = newArticleDao.getAllBookmarkedArticles().first()

            val searchResultsUsers = serverSearchResults.map { serverSearchResultUser ->
//                val isBookmarked = bookmarkedArticles.any { bookmarkedArticle ->
//                    bookmarkedArticle.url == serverSearchResultArticle.url
//                }
                SearchUser(
                    username = serverSearchResultUser.login,
                    avatarUrl = serverSearchResultUser.avatar_url
                )
//                NewsArticle(
//                    title = serverSearchResultArticle.title,
//                    url = serverSearchResultArticle.url,
//                    thumbnailUrl =  serverSearchResultArticle.urlToImage,
//                    isBookmarked
//                )
            }

            searchDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    searchUserDao.deleteSearchResultsForQuery(searchQuery)
                }

                val lastQueryPosition = searchUserDao.getLastQueryPosition(searchQuery) ?: 0
                var queryPosition = lastQueryPosition + 1

                val searchResults = searchResultsUsers.map { user ->
                    SearchResult(searchQuery, user.username, queryPosition++)
                }

                val nextPageKey = page + 1

                searchUserDao.insertUsers(searchResultsUsers)
                searchUserDao.insertSearchResults(searchResults)
                searchQueryRemoteKeyDao.insertRemoteKey(SearchQueryRemoteKey(searchQuery, nextPageKey))
            }

            return MediatorResult.Success(endOfPaginationReached = serverSearchResults.isEmpty())
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

}
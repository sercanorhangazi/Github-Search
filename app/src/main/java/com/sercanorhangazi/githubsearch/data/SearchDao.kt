package com.sercanorhangazi.githubsearch.data

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface SearchDao {

    @Query("select * from search_results sr inner join search_user su on sr.username = su.username where searchQuery = :query order by queryPosition")
    fun getSearchResultUsersPaged(query: String): PagingSource<Int, SearchUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(articles: List<SearchUser>)

    @Query("select max(queryPosition) from search_results where searchQuery = :searchQuery")
    suspend fun getLastQueryPosition(searchQuery: String): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchResults(searchResults: List<SearchResult>)

    @Query("delete from search_results where searchQuery=:query")
    suspend fun deleteSearchResultsForQuery(query: String)

}
package com.sercanorhangazi.githubsearch.data

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    // Search
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

    // User Details
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetail(userDetail: UserDetail)

    @Query("select * from user_details where username=:username")
    fun getUserDetails(username: String): Flow<UserDetail>

    @Update(entity = UserDetail::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserDetails(userDetail: UserDetail)

    @Query("delete from user_details where username=:username")
    suspend fun deleteUserDetails(username: String)
}
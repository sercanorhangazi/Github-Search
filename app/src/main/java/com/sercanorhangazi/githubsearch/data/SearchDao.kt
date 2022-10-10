package com.sercanorhangazi.githubsearch.data

import androidx.room.*
import com.sercanorhangazi.githubsearch.model.UserSearchResultModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchDao {

    @Query("select * from `search-result` where `query`=:query and page=:page")
    fun getSearchResultsFor(query: String, page: Int): Flow<UserSearchResultModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchResult(searchResult: UserSearchResultModel)

    @Query("delete from `search-result` where `query`=:query and page=:page")
    suspend fun deleteResultsFor(query: String, page: Int)

}
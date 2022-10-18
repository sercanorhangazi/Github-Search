package com.sercanorhangazi.githubsearch.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SearchQueryRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(remoteKey: SearchQueryRemoteKey)

    @Query("select * from search_query_remote_keys where searchQuery = :searchQuery")
    suspend fun getRemoteKey(searchQuery: String): SearchQueryRemoteKey

}
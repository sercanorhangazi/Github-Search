package com.sercanorhangazi.githubsearch.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchResult::class, SearchUser::class, SearchQueryRemoteKey::class,
                     UserDetail::class],
    version = 1,
    exportSchema = false)
abstract class SearchDatabase: RoomDatabase() {

    abstract fun searchDao(): SearchDao
    abstract fun searchQueryRemoteKeyDao(): SearchQueryRemoteKeyDao
}
package com.sercanorhangazi.githubsearch.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sercanorhangazi.githubsearch.model.UserSearchResultModel
import com.sercanorhangazi.githubsearch.pojo.SearchUserConverter

@Database(entities = [UserSearchResultModel::class], version = 1, exportSchema = false)
@TypeConverters(SearchUserConverter::class)
abstract class SearchDatabase: RoomDatabase() {

    abstract fun searchDao(): SearchDao
}
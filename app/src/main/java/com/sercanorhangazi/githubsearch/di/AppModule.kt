package com.sercanorhangazi.githubsearch.di

import android.app.Application
import androidx.room.Room
import com.sercanorhangazi.githubsearch.data.SearchDatabase
import com.sercanorhangazi.githubsearch.retrofit.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApi() = RetrofitInstance.api

    @Provides
    @Singleton
    fun productDatabase(app: Application): SearchDatabase =
        Room.databaseBuilder(app, SearchDatabase::class.java, "search_database")
            .build()
    
}
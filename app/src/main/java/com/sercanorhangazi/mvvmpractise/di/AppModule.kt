package com.sercanorhangazi.mvvmpractise.di

import com.sercanorhangazi.mvvmpractise.model.User
import com.sercanorhangazi.mvvmpractise.retrofit.RetrofitInstance
import com.sercanorhangazi.mvvmpractise.ui.UserSearchCellAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGithubApi() = RetrofitInstance.api

}
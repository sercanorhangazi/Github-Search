package com.sercanorhangazi.mvvmpractise.di

import com.sercanorhangazi.mvvmpractise.retrofit.RetrofitInstance
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
}
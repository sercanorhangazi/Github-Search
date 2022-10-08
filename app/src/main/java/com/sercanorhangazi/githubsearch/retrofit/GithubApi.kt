package com.sercanorhangazi.githubsearch.retrofit

import com.sercanorhangazi.githubsearch.model.UserDetail
import com.sercanorhangazi.githubsearch.model.UserSearchResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun getUserSearchResults(
        @Query("q") query: String,
        @Query("page") page: Int
    ): UserSearchResultModel

    @GET("users/{login}")
    suspend fun getUserDetails(
        @Path("login") username: String
    ): UserDetail
}
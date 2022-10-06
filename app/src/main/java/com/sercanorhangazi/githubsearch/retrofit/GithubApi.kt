package com.sercanorhangazi.githubsearch.retrofit

import com.sercanorhangazi.githubsearch.model.UserDetail
import com.sercanorhangazi.githubsearch.model.UserSearchResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    fun getUserSearchResults(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Call<UserSearchResultModel>

    @GET("users/{login}")
    fun getUserDetails(
        @Path("login") username: String
    ): Call<UserDetail>
}
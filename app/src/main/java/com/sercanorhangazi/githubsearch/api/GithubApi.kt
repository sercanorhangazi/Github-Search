package com.sercanorhangazi.githubsearch.api

import com.sercanorhangazi.githubsearch.data.UserDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): UserSearchResponse

    @GET("users/{login}")
    suspend fun fetchUserDetails(
        @Path("login") username: String
    ): UserDetailsDto
}
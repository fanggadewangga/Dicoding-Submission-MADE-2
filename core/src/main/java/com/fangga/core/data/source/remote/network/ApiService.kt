package com.fangga.core.data.source.remote.network

import com.fangga.core.data.source.remote.response.SearchResponse
import com.fangga.core.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Search user by query
    @GET("search/users")
    suspend fun searchUser(
        @Query("q")
        query: String
    ): SearchResponse

    // Get user detail
    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username")
        username: String
    ): UserResponse

    // Get user's followers
    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username")
        username: String
    ): List<UserResponse>

    // Get user's followings
    @GET("users/{username}/following")
    suspend fun getUserFollowings(
        @Path("username")
        username: String
    ): List<UserResponse>
}
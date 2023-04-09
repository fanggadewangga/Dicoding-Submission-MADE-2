package com.fangga.core.data.source.remote

import android.util.Log
import com.fangga.core.data.source.remote.network.ApiResponse
import com.fangga.core.data.source.remote.network.ApiService
import com.fangga.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllUsers(query: String): Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val userSearch = apiService.searchUser(query)
                val userArray = userSearch.items
                if (userArray.isEmpty()) {
                    emit(ApiResponse.Error(null))
                } else {
                    emit(ApiResponse.Success(userArray))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)


    suspend fun getAllFollowers(username: String): Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val userFollower = apiService.getUserFollowers(username)
                emit(ApiResponse.Success(userFollower))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getAllFollowing(username: String): Flow<ApiResponse<List<UserResponse>>> =
        flow {
            try {
                val userFollowing = apiService.getUserFollowings(username)
                emit(ApiResponse.Success(userFollowing))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getUserDetail(username: String): Flow<ApiResponse<UserResponse>> =
        flow {
            try {
                val userDetail = apiService.getUserDetail(username)
                emit(ApiResponse.Success(userDetail))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(RemoteDataSource::class.java.simpleName, e.localizedMessage)
            }
        }.flowOn(Dispatchers.IO)
}

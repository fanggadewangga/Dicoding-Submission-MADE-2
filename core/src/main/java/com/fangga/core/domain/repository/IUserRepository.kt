package com.fangga.core.domain.repository

import com.fangga.core.data.Resource
import com.fangga.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getAllUsers(query: String?): Flow<Resource<List<User>>>
    fun getAllFollowers(username: String): Flow<Resource<List<User>>>
    fun getAllFollowing(username: String): Flow<Resource<List<User>>>
    fun getDetailUser(username: String): Flow<Resource<User>>
    fun getFavoriteUser(): Flow<List<User>>
    fun getDetailState(username: String): Flow<User>?
    fun getTheme(): Flow<Boolean>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User): Int
    suspend fun saveTheme(isLightModeActive: Boolean)
}
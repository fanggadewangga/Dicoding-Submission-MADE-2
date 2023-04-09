package com.fangga.core.data.source.local

import com.fangga.core.data.datastore.UserDataStore
import com.fangga.core.data.source.local.entity.UserEntity
import com.fangga.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userDao: UserDao, private val userDataStore: UserDataStore) {
    fun getFavoriteUser(): Flow<List<UserEntity>> = userDao.getFavorites()
    fun getFavoritedUserDetail(username: String) = userDao.getFavoritedUserDetail(username)
    fun getTheme() = userDataStore.getThemeSetting()
    suspend fun insertFavoritedUser(user: UserEntity) = userDao.insertFavoritedUser(user)
    suspend fun deleteUser(user: UserEntity) = userDao.deleteFavoritedUser(user)
    suspend fun saveTheme(isLightModeActive: Boolean) = userDataStore.saveThemeSetting(isLightModeActive)
}
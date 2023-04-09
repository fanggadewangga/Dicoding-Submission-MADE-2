package com.fangga.core.data.source.local.room

import androidx.room.*
import com.fangga.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY username ASC")
    fun getFavorites(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE username =:username")
    fun getFavoritedUserDetail(username: String): Flow<UserEntity>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoritedUser(user: UserEntity?)

    @Delete
    suspend fun deleteFavoritedUser(user: UserEntity): Int
}
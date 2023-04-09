package com.fangga.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fangga.core.data.source.local.entity.UserEntity

@Database(entities = [UserEntity::class], exportSchema = false, version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
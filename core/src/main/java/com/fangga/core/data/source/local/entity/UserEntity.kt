package com.fangga.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int? = 0,

    @ColumnInfo(name = "username")
    val username: String? = "",

    @ColumnInfo(name = "name")
    val name: String? = "",

    @ColumnInfo(name = "location")
    val location: String? = "",

    @ColumnInfo(name = "company")
    val company: String? = "",

    @ColumnInfo(name = "repository")
    val repository: Int? = 0,

    @ColumnInfo(name = "follower")
    val follower: Int? = 0,

    @ColumnInfo(name = "following")
    val following: Int? =0,

    @ColumnInfo(name = "avatar_url")
    val avatar: String? = "",

    @ColumnInfo(name = "isFavorite")
    var isFavorite:Boolean? = false
)
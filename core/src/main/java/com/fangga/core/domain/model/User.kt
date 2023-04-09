package com.fangga.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int?,
    val username: String?,
    val name: String?,
    val location: String?,
    val company: String?,
    val repository: Int?,
    val follower: Int?,
    val following: Int?,
    val avatar: String?,
    var isFavorite: Boolean?
): Parcelable

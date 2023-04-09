package com.fangga.core.data.source.remote.response

import com.squareup.moshi.Json


data class UserResponse(
    @field:Json(name = "id")
    val id: Int? = 0,

    @field:Json(name = "login")
    val username: String? = "",

    @field:Json(name = "name")
    val name: String? = "",

    @field:Json(name = "location")
    val location: String? = "",

    @field:Json(name = "company")
    val company: String? = "",

    @field:Json(name = "public_repos")
    val repository: Int? = 0,

    @field:Json(name = "followers")
    val follower: Int? = 0,

    @field:Json(name = "following")
    val following: Int? =0,

    @field:Json(name = "avatar_url")
    val avatar: String? = "",
)
package com.fangga.core.data.source.remote.response

import com.squareup.moshi.Json

data class SearchResponse(
    @field:Json(name = "items")
    val items: List<UserResponse>
)
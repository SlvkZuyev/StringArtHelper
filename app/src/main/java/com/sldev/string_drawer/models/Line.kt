package com.sldev.string_drawer.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Line(
    @Json(name = "start")
    val start: Pair<Float, Float>,
    @Json(name = "end")
    val end: Pair<Float, Float>
)
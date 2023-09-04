package com.sldev.string_drawer.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class InstructionProgress(
    @Json(name = "steps")
    val steps: List<Int> = emptyList(),
    @Json(name = "current_step_index")
    val currentStepIndex: Int = 0
)
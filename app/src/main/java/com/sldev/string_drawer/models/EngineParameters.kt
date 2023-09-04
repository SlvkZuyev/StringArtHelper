package com.sldev.string_drawer.models

data class EngineParameters(
    var grayDelta: Float = 80f,
    var darkModifier: Float = 1f,
    var lightModifier: Float = -5f,
    var linesCount: Int = 2000,
    val ankersCount: Int = 240,
    val minimalAnkersDistance: Int = 40
)
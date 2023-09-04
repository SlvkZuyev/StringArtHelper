package com.sldev.string_drawer.utils

import androidx.compose.ui.geometry.Offset

fun Offset(value: Pair<Float, Float>, scale: Float): Offset {
    return Offset(x = value.first * scale, y = value.second * scale)
}
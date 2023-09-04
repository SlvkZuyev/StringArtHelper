package com.sldev.string_drawer.extensions

fun Pair<Int, Int>.toFloat(): Pair<Float, Float> {
    return Pair(first.toFloat(), second.toFloat())
}
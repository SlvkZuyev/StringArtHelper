package com.sldev.string_drawer

interface LineComparator {
    fun compareLines(currentLine: List<Int>, originalLine: List<Int>): Float

    fun getTitle(): String{
        return this::class.simpleName.toString()
    }

    fun chooseBestLine(lines: Map<Int, Float>): Pair<Int, Float>
}
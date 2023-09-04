package com.sldev.string_drawer.extensions

fun List<Pair<Int, Int>>.getPairWithMaxValue(): Pair<Int, Int>{
    var max = get(0)
    for (value in this) {
        if (value.second > max.second)
            max = value
    }
    return max
}

fun List<Pair<Int, Int>>.getPairWithMinValue(): Pair<Int, Int>{
    var min = get(0)
    for (value in this) {
        if (value.second < min.second)
            min = value
    }
    return min
}

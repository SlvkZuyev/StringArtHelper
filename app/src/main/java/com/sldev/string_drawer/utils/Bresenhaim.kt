package com.sldev.string_drawer.utils


/** function findLine() - to find that belong to line connecting the two points  */
fun bresenhaim(start: Pair<Int, Int>, end: Pair<Int, Int>): List<Pair<Int, Int>> {
    var x0 = start.first
    var y0 = start.second
    val x1 = end.first
    val y1 = end.second
    val line: MutableList<Pair<Int, Int>> = mutableListOf()
    val dx = Math.abs(x1 - x0)
    val dy = Math.abs(y1 - y0)
    val sx = if (x0 < x1) 1 else -1
    val sy = if (y0 < y1) 1 else -1
    var err = dx - dy
    var e2: Int
    while (true) {
        line.add(Pair(x0, y0))
        if (x0 == x1 && y0 == y1) break
        e2 = 2 * err
        if (e2 > -dy) {
            err = err - dy
            x0 = x0 + sx
        }
        if (e2 < dx) {
            err = err + dx
            y0 = y0 + sy
        }
    }
    return line
}
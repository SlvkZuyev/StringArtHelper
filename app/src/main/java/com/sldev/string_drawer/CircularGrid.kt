package com.sldev.string_drawer

import android.graphics.Point
import android.util.Log
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class CircularGrid {
    val width = 20
    val height = 20
    val matrix = MutableList(width) {
        MutableList(height) { "_" }
    }

    fun testDots() {
        val dots = calculateCircularDots(dotsCount = 20, center = Point(10, 10), radius = 5)
        for (dot in dots) {
            matrix[dot.x][dot.y] = "#"
        }
        printMatrix()
    }

    fun printMatrix() {
        var result = ""
        for (line in matrix) {
            for (item in line) {
                result += "$item "
            }
            result += "\n"
        }
        Log.d("Circular", "Matrix: \n$result")
    }

}

fun calculateCircularDots(dotsCount: Int, center: Point, radius: Int): List<Point> {
    val dots: MutableList<Point> = mutableListOf()
    val delataAngel = 360 / dotsCount.toFloat()
    var curranAngle = 0.0
    for (nDot in 0 until dotsCount) {
        val cosAngle = cos(Math.toRadians(curranAngle))
        val sinAngle = sin(Math.toRadians(curranAngle))
//        Log.d(
//            "Circular",
//            "Calculateing angle N = $nDot, curentAngle = $curranAngle, cos = $cosAngle, sin = $sinAngle"
//        )
        val x = center.x + radius * cosAngle
        val y = center.y + radius * sinAngle
        dots.add(Point(x.roundToInt(), y.roundToInt()))
        curranAngle += delataAngel
    }
    Log.d(
        "Circular",
        "Dots calculated deltaNagle: $delataAngel, center: $center, radius: $radius, dots: $dots"
    )
    return dots
}

//package com.sldev.string_drawer
//
//import android.graphics.Point
//import android.util.Log
//import com.sldev.string_drawer.extensions.getPairWithMinValue
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import java.lang.Math.abs
//
//class LinesEngine(
//    private val image: List<List<Int>>,
//    private val imageWidth: Int,
//    private val imageHeight: Int,
//    private val grayDelta: Int,
//    private val darkModifier: Int,
//    private val anckersCount: Int,
//) {
//    private val maxIndex = imageWidth * 2 + imageHeight * 2
//    private var currentImage = MutableList(imageWidth) {
//        MutableList(imageHeight) { 0 }
//    }
//    val anckers = calculateCircularDots(
//        dotsCount = anckersCount,
//        center = Point(imageWidth / 2, imageHeight / 2),
//        radius = imageWidth / 2
//    )
//
//    init {
//////        testApplyLine()
////        var result = ""
////        for (line in image) {
////            for (item in line) {
//////                result += "${String.format("#%06X", 0xFFFFFF and item)} "
////                result += " $item"
////            }
////            result += "\n"
////        }
////        Log.d("SlvkLog", "ORIGINAL IMAGE:\n $result")
//    }
//
//    fun testBresenhaim() {
//        var result = ""
//        val testMatrix = MutableList(10) {
//            MutableList(10) { "0" }
//        }
//        val bresenhemLine = bres(Pair(0, 2), Pair(8, 6))
//        for (item in bresenhemLine) {
//            testMatrix[item.first][item.second] = "$"
//        }
//
//        for (line in testMatrix) {
//            for (item in line) {
//                result += "$item "
//            }
//            result += "\n"
//        }
//    }
//
//
//    fun calculateLines(nLines: Int, onCreated: (List<Line>) -> Unit) {
//        CoroutineScope(Dispatchers.Default).launch {
//            var currentIndex = 0
//            val result = mutableListOf<Pair<Int, Int>>()
//            repeat(nLines) {
//                val destination = chooseBestLinePosition(currentIndex)
//                applyLine(currentIndex, destination)
//                result.add(Pair(currentIndex, destination))
//                currentIndex = destination
//            }
//            val ret = result.map {
//                Line(
//                    start = indexToCoordinates(it.first).toFloat(),
//                    end = indexToCoordinates(it.second).toFloat()
//                )
//            }
//            printCreatedImage()
//            onCreated(ret)
//        }
//    }
//
//    fun printCreatedImage() {
//        var logRes = ""
//        for (line in currentImage) {
//            for (item in line) {
////                result += "${String.format("#%06X", 0xFFFFFF and item)} "
//                logRes += " $item"
//            }
//            logRes += "\n"
//        }
//        Log.d("SlvkLog", "CREATED IMAGE:\n $logRes")
//    }
//
//    private fun chooseBestLinePosition(start: Int): Int {
//        val values = mutableListOf<Pair<Int, Int>>()
//        for (i in 0 until maxIndex) {
//            values.add(Pair(i, calculateLineValue(start = start, end = i)))
//        }
//
//        val minDelta = values.getPairWithMinValue()
//        Log.d("SlvkLog", "For start: $start, best dest is $minDelta with delta: ${minDelta.second}")
//        return minDelta.first
//    }
//
//    private fun applyLine(start: Int, end: Int) {
//        val line = getLinePixels(start, end)
//        Log.d("SlvkLog", "Applyeing inde for coordinates: start $start, end $end, pixels: $line")
//        for (item in line) {
//            currentImage[item.first][item.second] += grayDelta
//        }
//    }
//
//    val lightModifier = 50
//    private fun calculateLineValue(start: Int, end: Int): Int {
//        if (start == end) return Int.MAX_VALUE
//        val linePixelsCoordinates = getLinePixels(start, end)
//        if (linePixelsCoordinates.isEmpty()) return Int.MAX_VALUE
//        var value = 0
//        for ((index, pixel) in linePixelsCoordinates.withIndex()) {
//            val pixelFromCurrentImage = currentImage[pixel.first][pixel.second] + grayDelta
//            val pixelFromOriginalImage = image[pixel.first][pixel.second]
//            var delta = pixelFromOriginalImage - pixelFromCurrentImage
//            if (delta < 0) {
//                delta = abs(delta) * darkModifier
//            } else {
//                delta = 0
//            }
////
////            Log.d(
////                "SlvkLog",
////                "Current: $pixelFromCurrentImage, original^ $pixelFromOriginalImage Delta for index $index is $delta, "
////            )
////            if(delta < 0) return 0
//            value += delta
//        }
//        return value / linePixelsCoordinates.size
//    }
//
//    fun getLinePixels(start: Int, end: Int): List<Pair<Int, Int>> {
//        val startCoords = indexToCoordinates(start)
//        val endCoords = indexToCoordinates(end)
//        return findLine(startCoords, endCoords)
//    }
//
//    fun bresenham(start: Pair<Int, Int>, end: Pair<Int, Int>): List<Pair<Int, Int>> {
//        val mNew = 2 * (end.second - start.second)
//        var slopeError = mNew - (end.first - start.first)
//        var x = start.first
//        var y = start.second
//        val result = mutableListOf<Pair<Int, Int>>()
//        result.add(start)
//
//        while (x < end.first) {
//            x++
//            slopeError += mNew
//            result.add(Pair(x, y))
//            if (slopeError >= 0) {
//                y++
//                slopeError -= 2 * (end.first - start.first)
//            }
//        }
//
//        Log.d("SlvkLog", "Bresenh result for st =$start.  end =$end is $result")
//        return result
//    }
//
//    fun getBoxLines(): List<Line> {
//        val result = mutableListOf<Pair<Int, Int>>()
//        result.add(Pair(0, 59))
//        result.add(Pair(59, 119))
//        result.add(Pair(119, 179))
//        result.add(Pair(179, 239))
//        return result.map {
//            Line(
//                start = indexToCoordinates(it.first).toFloat(),
//                end = indexToCoordinates(it.second).toFloat()
//            )
//        }
//    }
//
//    fun indexToCoordinates(index: Int): Pair<Int, Int> {
//        return Pair(anckers[index].x, anckers[index].y)
////        if (index in (0 until imageWidth)) {
////            return Pair(index, 0)
////        }
////        if (index in (imageWidth until imageWidth + imageHeight)) {
////            return Pair(imageWidth - 1, index - imageWidth)
////        }
////        if (index in (imageWidth + imageHeight until imageWidth * 2 + imageHeight)) {
////            return Pair((imageWidth * 2 + imageHeight) - index - 1, imageHeight - 1)
////        }
////        return Pair(0, 2 * imageWidth + 2 * imageHeight - index - 1)
//    }
//
//
//    fun testLineValueCalculation() {
//        val value = calculateLineValue(0, 9)
////        Log.d("SlvkLog", "Value line delta: $value")
//    }
//
//    private fun testApplyLine() {
//        applyLine(0, 59)
//        applyLine(59, 119)
//        applyLine(119, 179)
//        applyLine(179, 239)
//        printCreatedImage()
//    }
//}
//
//fun Pair<Int, Int>.toFloat(): Pair<Float, Float> {
//    return Pair(first.toFloat(), second.toFloat())
//}
//
//
//fun bres(start: Pair<Int, Int>, end: Pair<Int, Int>): List<Pair<Int, Int>> {
//    val dx = Math.abs(end.first - start.first);
//    val dy = Math.abs(end.second - start.second);
//    // If slope is less than one
//    return if (dx > dy) {
//        plotPixel(start, end, dx, dy, 0);
//    }
//    // if slope is greater than or equal to 1
//    else {
//        // passing argument as 1 to plot (y,x)
//        plotPixel(start, end, dy, dx, 1);
//    }
//}
//
//fun plotPixel(
//    start: Pair<Int, Int>, end: Pair<Int, Int>,
//    dx: Int,
//    dy: Int,
//    decide: Int
//): List<Pair<Int, Int>> {
//    var x1 = start.first
//    var y1 = start.second
//    var pk = 2 * dy - dx
//    val result = mutableListOf<Pair<Int, Int>>()
//    for (i in 0..dx) {
//        result.add(Pair(x1, y1))
//        // checking either to decrement or increment the
//        // value if we have to plot from (0,100) to
//        // (100,0)
//        if (x1 < end.first) x1++ else x1--
//        pk = if (pk < 0) {
//            // decision value will decide to plot
//            // either  x1 or y1 in x's position
//            if (decide == 0) {
//                pk + 2 * dy
//            } else pk + 2 * dy
//        } else {
//            if (y1 < end.second) y1++ else y1--
//            pk + 2 * dy - 2 * dx
//        }
//    }
//    return result
//}
//
///** function findLine() - to find that belong to line connecting the two points  */
//fun findLine(start: Pair<Int, Int>, end: Pair<Int, Int>): List<Pair<Int, Int>> {
//    var x0 = start.first
//    var y0 = start.second
//    val x1 = end.first
//    val y1 = end.second
//    val line: MutableList<Pair<Int, Int>> = mutableListOf()
//    val dx = abs(x1 - x0)
//    val dy = abs(y1 - y0)
//    val sx = if (x0 < x1) 1 else -1
//    val sy = if (y0 < y1) 1 else -1
//    var err = dx - dy
//    var e2: Int
//    while (true) {
//        line.add(Pair(x0, y0))
//        if (x0 == x1 && y0 == y1) break
//        e2 = 2 * err
//        if (e2 > -dy) {
//            err = err - dy
//            x0 = x0 + sx
//        }
//        if (e2 < dx) {
//            err = err + dx
//            y0 = y0 + sy
//        }
//    }
//    return line
//}
//

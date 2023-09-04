package com.sldev.string_drawer

import android.graphics.Point
import android.speech.tts.TextToSpeech.Engine
import android.util.Log
import com.sldev.string_drawer.extensions.getPairWithMinValue
import com.sldev.string_drawer.extensions.toFloat
import com.sldev.string_drawer.models.EngineParameters
import com.sldev.string_drawer.models.Line
import com.sldev.string_drawer.models.LinesCalculationResult
import com.sldev.string_drawer.utils.bresenhaim
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.log
import kotlin.math.roundToInt

class CircularLineEngine(
    private val image: List<List<Int>>,
    private val parameters: EngineParameters,
    private val radius: Int,
    private val onProgressChange: (Float) -> Unit = {}
) {

    init {
        Log.d("CircularLineEngine", "Engine created with params: $parameters, radius: $radius")
    }

    private var currentImage = MutableList(radius * 2) {
        MutableList(radius * 2) { 0 }
    }
    private val anckers = calculateCircularDots(
        dotsCount = parameters.ankersCount,
        center = Point(radius, radius),
        radius = radius - 1
    )


    suspend fun calculateLines(): LinesCalculationResult = withContext(Dispatchers.Default) {
        var currentIndex = 0
        val result = mutableListOf<Pair<Int, Int>>()
        repeat(parameters.linesCount) {
            val destination = chooseBestLinePosition(currentIndex)
            applyLine(currentIndex, destination)
            result.add(Pair(currentIndex, destination))
            currentIndex = destination
            val progress = it / parameters.linesCount.toFloat()
            onProgressChange(progress)
            Log.d("CircularLineEngineP", "Progress: $progress")
            Log.d("CircularLineEngine", "Cycle N: $it / ${parameters.linesCount}")
        }

        val resultLines = result.map {
            Line(
                start = indexToCoordinates(it.first).toFloat(),
                end = indexToCoordinates(it.second).toFloat()
            )
        }
//        printCreatedImage()
        return@withContext LinesCalculationResult(
            linesCoordinates = resultLines,
            indexedLines = result,
            engineParameters = parameters
        )

    }

    private fun printCreatedImage() {
        var logRes = ""
        for (line in currentImage) {
            for (item in line) {
//                result += "${String.format("#%06X", 0xFFFFFF and item)} "
                logRes += " $item"
            }
            logRes += "\n"
        }
        Log.d("SlvkLog", "CREATED IMAGE:\n $logRes")
    }

    private fun getAvailableAnkersRange(start: Int, distance: Int): Iterable<Int> {
        var bottomBorder = start - distance
        var topBorder = start + distance

        val bottomRange = if (bottomBorder < 0) {
            topBorder += bottomBorder
            0..0
        } else {
            0..bottomBorder
        }

        val topRange = if (topBorder > parameters.ankersCount) {
            bottomBorder += topBorder - parameters.ankersCount
            parameters.ankersCount - 1 until parameters.ankersCount
        } else {
            topBorder until parameters.ankersCount
        }

        val result = bottomRange + topRange
        Log.d("SlvkLog", "Range: startPoint: $start | distance: $distance | result: $result")
        return result
    }

    private fun chooseBestLinePosition(start: Int): Int {
        var minPair = Pair(0, Int.MAX_VALUE)
        for (anckerIndex in getAvailableAnkersRange(start, parameters.minimalAnkersDistance)) {
            val value = calculateLineValueWithByMedian2(start = start, end = anckerIndex)
            if(value < minPair.second){
                minPair = Pair(anckerIndex, value)
            }
        }

        Log.d("SlvkLog", "For start: $start, best dest is $minPair with delta: ${minPair.second}")
        return minPair.first
    }

    private fun applyLine(start: Int, end: Int) {
        val line = getLinePixels(start, end)
        Log.d("SlvkLog", "Applyeing inde for coordinates: start $start, end $end, pixels: $line")
        for (item in line) {
            currentImage[item.first][item.second] += parameters.grayDelta.toInt()
        }
    }

    private fun calculateLineValueByDarkestPixels(start: Int, end: Int): Int {
        if (start == end) return 0
        val linePixelsCoordinates = getLinePixels(start, end)
        if (linePixelsCoordinates.isEmpty()) return 0
        var value = 0
        for ((index, pixel) in linePixelsCoordinates.withIndex()) {
            val pixelFromCurrentImage =
                currentImage[pixel.first][pixel.second] + parameters.grayDelta.toInt()
            val pixelFromOriginalImage = image[pixel.first][pixel.second]
            var delta = pixelFromOriginalImage - pixelFromCurrentImage
            if (delta < 0) {
                delta = (delta * parameters.darkModifier).roundToInt()
            }
            value += delta
        }
        return value
    }

    private fun calculateLineValueWithByMedian(start: Int, end: Int): Int {
        if (start == end) return Int.MAX_VALUE
        val linePixelsCoordinates = getLinePixels(start, end)
        if (linePixelsCoordinates.isEmpty()) return Int.MAX_VALUE
        var value = 0
        for ((index, pixel) in linePixelsCoordinates.withIndex()) {
            val pixelFromCurrentImage =
                currentImage[pixel.first][pixel.second] + parameters.grayDelta.toInt()
            val pixelFromOriginalImage = image[pixel.first][pixel.second]
            var delta = pixelFromOriginalImage - pixelFromCurrentImage
            if (delta < 0) {
                delta = (abs(delta) * parameters.darkModifier).toInt()
            } else {
                delta = 0
            }
            value += delta
        }
        return value / linePixelsCoordinates.size
    }

    private fun calculateLineValueWithByMedian2(start: Int, end: Int): Int {
        if (start == end) return Int.MAX_VALUE
        val linePixelsCoordinates = getLinePixels(start, end)
        if (linePixelsCoordinates.isEmpty()) return Int.MAX_VALUE
        var punishmentValue = 0
        for (pixel in linePixelsCoordinates) {
            val pixelFromCurrentImage =
                currentImage[pixel.first][pixel.second] + parameters.grayDelta.toInt()
            val pixelFromOriginalImage = image[pixel.first][pixel.second]
            var delta = pixelFromOriginalImage - pixelFromCurrentImage
            delta = if (delta < 0) {
                (abs(delta) * parameters.darkModifier).toInt()
            } else {
                (delta * parameters.lightModifier).toInt()
            }
            punishmentValue += delta
        }
        return punishmentValue / linePixelsCoordinates.size
    }

    private fun getLinePixels(start: Int, end: Int): List<Pair<Int, Int>> {
        val startCoords = indexToCoordinates(start)
        val endCoords = indexToCoordinates(end)
        return bresenhaim(startCoords, endCoords)
    }

    private fun indexToCoordinates(index: Int): Pair<Int, Int> {
        return Pair(anckers[index].x, anckers[index].y)
    }

    fun getAnckerTestLines(): List<Line> {
        val lines = mutableListOf<Line>()
        for ((index, ancker) in anckers.withIndex()) {
            if (index != anckers.lastIndex) {
                val nexAncker = anckers[index + 1]
                lines.add(
                    Line(
                        start = Pair(ancker.x, ancker.y).toFloat(),
                        end = Pair(nexAncker.x, nexAncker.y).toFloat()
                    )
                )
            }
        }
        return lines
    }

    fun getTestGetPixelsLines(): List<Line> {
        return listOf(
            getLinePixels(0, 100).toLine(),
            getLinePixels(0, 200).toLine(),
            getLinePixels(0, 300).toLine(),
            getLinePixels(0, 400).toLine(),
        )
    }
}

fun List<Pair<Int, Int>>.toLine(): Line {
    val firstPoint = first()
    val lastPoint = last()
    return Line(
        start = Pair(firstPoint.first, firstPoint.second).toFloat(),
        end = Pair(lastPoint.first, lastPoint.second).toFloat()
    )
}


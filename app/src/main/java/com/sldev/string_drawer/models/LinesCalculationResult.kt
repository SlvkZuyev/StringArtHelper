package com.sldev.string_drawer.models

data class LinesCalculationResult(
    val linesCoordinates: List<Line> = emptyList(),
    val indexedLines: List<Pair<Int, Int>> = emptyList(),
    val engineParameters: EngineParameters = EngineParameters()
)
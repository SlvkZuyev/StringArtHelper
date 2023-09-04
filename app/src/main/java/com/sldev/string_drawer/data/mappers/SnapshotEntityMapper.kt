package com.sldev.string_drawer.data.mappers


import com.sldev.string_drawer.ImageDescription
import com.sldev.string_drawer.data.entities.SnapshotEntity
import com.sldev.string_drawer.models.*
import com.squareup.moshi.*

val moshi: Moshi = Moshi.Builder()
    .add(PairAdapter())
    .build()

@OptIn(ExperimentalStdlibApi::class)
val linesJsonAdapter = moshi.adapter<List<Line>>()


class PairAdapter {
    @ToJson
    fun toJson(pair: Pair<Float, Float>): String {
        return "${pair.first}|${pair.second}"
    }

    @FromJson
    fun fromJson(pair: String): Pair<Float, Float> {
        val values = pair.split("|")
        val first = values[0].toFloat()
        val second = values[1].toFloat()
        return Pair(first, second)
    }
}

fun SnapshotEntity.toSnapshot(): Snapshot {

    val engineParameters = EngineParameters(
        grayDelta = grayDelta,
        darkModifier = darkModifier,
        lightModifier = lightModifier,
        linesCount = nLines,
        ankersCount = anckersCount
    )

    val imageParameters = ImageParameters(
        strokeWidth = strokeWidth,
        alpha = alpha
    )

    return Snapshot(
        lines = linesJsonAdapter.fromJson(jsonImage) ?: emptyList(),
        engineParameters = engineParameters,
        imageParameters = imageParameters
    )
}

fun Snapshot.toSnapshotEntity(): SnapshotEntity {
    return SnapshotEntity(
        imageDescription = ImageDescription,
        jsonImage = linesJsonAdapter.toJson(lines),
        grayDelta = engineParameters.grayDelta,
        darkModifier = engineParameters.darkModifier,
        lightModifier = engineParameters.lightModifier,
        nLines = engineParameters.linesCount,
        strokeWidth = imageParameters.strokeWidth,
        alpha = imageParameters.alpha,
        anckersCount = engineParameters.ankersCount
    )
}
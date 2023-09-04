package com.sldev.string_drawer.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "snapshots")
class SnapshotEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imageDescription: String,
    val jsonImage: String,
    val grayDelta: Float,
    val darkModifier: Float,
    val lightModifier: Float,
    val nLines: Int,
    val strokeWidth: Float,
    val alpha: Float,
    val anckersCount: Int,
)
package com.sldev.string_drawer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sldev.string_drawer.data.entities.SnapshotEntity


@Database(entities = [SnapshotEntity::class], version = 1)
abstract class SnapshotDatabase : RoomDatabase() {
    abstract fun snapshotDao(): SnapshotDao
}

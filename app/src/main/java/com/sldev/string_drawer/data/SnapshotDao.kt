package com.sldev.string_drawer.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sldev.string_drawer.data.entities.SnapshotEntity

@Dao
interface SnapshotDao {


    @Query("SELECT * FROM snapshots")
    suspend fun getAllSnapshots(): List<SnapshotEntity>

    @Insert
    suspend fun addSnapshot(snapshotEntity: SnapshotEntity)

}
package com.sldev.string_drawer.data

import com.sldev.string_drawer.data.mappers.toSnapshot
import com.sldev.string_drawer.data.mappers.toSnapshotEntity
import com.sldev.string_drawer.models.Snapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SnapshotsRepository @Inject constructor(private val dao: SnapshotDao) {

    suspend fun getAllSnapshots(): List<Snapshot> = withContext(Dispatchers.IO) {
        try {
            val entities = dao.getAllSnapshots()
            return@withContext entities.map { it.toSnapshot() }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList()
        }
    }

    suspend fun addSnapshot(snapshot: Snapshot) = withContext(Dispatchers.IO) {
        try {
            dao.addSnapshot(snapshot.toSnapshotEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncPaperArray

@Dao
interface SyncPaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncBatchArray: List<SyncPaperArray>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncBatchArray: SyncPaperArray)
}
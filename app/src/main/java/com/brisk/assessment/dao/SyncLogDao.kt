package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncLogArray

@Dao
interface SyncLogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncBatchArray: List<SyncLogArray>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncBatchArray: SyncLogArray)
}
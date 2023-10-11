package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncBatchArray

@Dao
interface SyncBatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncBatchArray: SyncBatchArray)
}
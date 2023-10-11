package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncImageArray

@Dao
interface SyncImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncBatchArray: List<SyncImageArray>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncBatchArray: SyncImageArray)
}
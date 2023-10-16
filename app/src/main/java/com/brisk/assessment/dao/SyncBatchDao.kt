package com.brisk.assessment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.brisk.assessment.model.SyncBatchArray

@Dao
interface SyncBatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncBatchArray: SyncBatchArray)

    @Update
    suspend fun update(syncBatchArray: SyncBatchArray)
    @Query("SELECT EXISTS (SELECT 1 FROM sync_batch_arr WHERE batch_id = :batchId)")
    fun isBatchIdExists(batchId: String) : Boolean

    @Query("Select * from sync_batch_arr")
    fun getSyncBatchArrayList() : LiveData<List<SyncBatchArray>>
}
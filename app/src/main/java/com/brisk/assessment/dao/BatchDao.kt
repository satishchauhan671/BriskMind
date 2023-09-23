package com.brisk.assessment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.UserResponse

@Dao
interface BatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(batchEntity: List<BatchRes>)


    @Query("Select * from batch_mst")
    fun getBatchData() : LiveData<List<BatchRes>>

    @Query("delete from batch_mst")
    fun deleteAll()

    @Query("select * from batch_mst where batch_id = :batchId")
    fun getBatchByBatchId(batchId : String) :  LiveData<BatchRes>

    @Query("select duration from batch_mst where batch_id = :batchId")
    fun getPaperDuration(batchId : String) : LiveData<String>

}
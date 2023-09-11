package com.brisk.assessment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.BatchRes

@Dao
interface BatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(batchEntity: List<BatchRes>)


    @Query("Select * from batch_mst")
    fun getBatchData() : LiveData<List<BatchRes>>

    @Query("delete from batch_mst")
    fun deleteAll()
}
package com.brisk.assessment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.BatchConfigRes
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.ImportAssessmentResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.PaperResponse

@Dao
interface BatchConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(batchConfigEntity: BatchConfigRes)


    @Query("Select * from batch_config_mst")
    fun getBatchData() : List<BatchConfigRes>

    @Query("select * from batch_config_mst where batch_id = :batchId")
    fun getBatchConfigByBatchId(batchId : String) : LiveData<BatchConfigRes>

    @Query("delete from batch_config_mst")
    fun deleteAllBatchConfig()
}
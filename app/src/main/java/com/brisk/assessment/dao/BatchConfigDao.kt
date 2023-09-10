package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.BatchConfigRes
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.ImportAssessmentResponse
import com.brisk.assessment.model.LanguageRes

@Dao
interface BatchConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(batchConfigEntity: BatchConfigRes)


    @Query("Select * from batch_config_mst")
    fun getBatchData() : List<BatchConfigRes>
}
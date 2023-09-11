package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.ImportAssessmentResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.PaperResponse

@Dao
interface PaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paperResponse: PaperResponse)


    @Query("Select * from paper_mst")
    fun getPaperData() : List<PaperResponse>

    @Query("delete from paper_mst")
    fun deleteAllPapers()
}
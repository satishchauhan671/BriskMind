package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.BatchConfigRes
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportAssessmentResponse
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.UserResponse

@Dao
interface ImportLanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(importLanguageResponse: List<ImportLanguageResponse>)


    @Query("Select * from import_language_mst")
    fun getImportLanguageData() : List<ImportLanguageResponse>
}
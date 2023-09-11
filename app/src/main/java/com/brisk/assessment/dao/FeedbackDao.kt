package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.BatchConfigRes
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportAssessmentResponse
import com.brisk.assessment.model.LanguageRes

@Dao
interface FeedbackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(feedbackResponse: List<FeedbackResponse>)


    @Query("Select * from feedback_mst")
    fun getFeedbackData() : List<FeedbackResponse>

    @Query("delete from feedback_mst")
    fun deleteAllFeedback()
}
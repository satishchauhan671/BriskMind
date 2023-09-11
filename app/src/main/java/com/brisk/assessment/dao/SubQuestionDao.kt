package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.SubQuestionResponse

@Dao
interface SubQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subQuestionResponse: List<SubQuestionResponse>)


    @Query("Select * from sub_question_mst")
    fun getSubQuestionsData() : List<SubQuestionResponse>


    @Query("delete from sub_question_mst")
    fun deleteAllSubQuestion()
}
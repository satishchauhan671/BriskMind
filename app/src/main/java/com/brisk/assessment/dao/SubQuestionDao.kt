package com.brisk.assessment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.SubQuestionResponse
import com.brisk.assessment.model.UserResponse

@Dao
interface SubQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subQuestionResponse: List<SubQuestionResponse>)


    @Query("Select * from sub_question_mst")
    fun getSubQuestionsData() : List<SubQuestionResponse>


    @Query("delete from sub_question_mst")
    fun deleteAllSubQuestion()


    @Query("select * from sub_question_mst where question_id = :queId")
    fun getSubQueByQueId(queId : String) : LiveData<List<SubQuestionResponse>>
}
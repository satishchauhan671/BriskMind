package com.brisk.assessment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.OptionRes
import com.brisk.assessment.model.UserResponse

@Dao
interface OptionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(optionRes: List<OptionRes>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(optionRes: OptionRes)

    @Query("Select * from option_mst")
    fun getOptionData() : LiveData<List<OptionRes>>

    @Query("delete from option_mst")
    fun deleteAll()

    @Query("select * from option_mst where subQuestionId = :subQuesId and option != ''")
    fun getOptionBySubQuestionId(subQuesId : String) :  LiveData<List<OptionRes>>
}
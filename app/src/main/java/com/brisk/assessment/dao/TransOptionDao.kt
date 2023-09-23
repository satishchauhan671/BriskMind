package com.brisk.assessment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.OptionRes
import com.brisk.assessment.model.TransOptionRes

@Dao
interface TransOptionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transOptionRes: TransOptionRes)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(transOptionRes: List<TransOptionRes>)


    @Query("Select * from trans_option_mst")
    fun getTransOptionData() : LiveData<List<TransOptionRes>>

    @Query("delete from trans_option_mst")
    fun deleteAll()

    @Query("select * from trans_option_mst where id = :transOptionId")
    fun getTransOptionById(transOptionId : String) :  LiveData<TransOptionRes>


    @Query("select * from trans_option_mst where id = :transOptionId and option != ''")
    fun getTransOptionBySubQuestionId(transOptionId : String) :  LiveData<List<TransOptionRes>>
}
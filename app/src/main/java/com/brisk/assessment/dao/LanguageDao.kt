package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.LanguageRes

@Dao
interface LanguageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(languagesEntity: List<LanguageRes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(languagesEntity: LanguageRes)

    @Query("Select * from languages")
    fun getLanguageList() : List<LanguageRes>
}
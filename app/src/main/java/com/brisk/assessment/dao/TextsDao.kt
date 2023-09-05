package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.TextsRes

@Dao
interface TextsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(textsEntity: TextsRes)
}
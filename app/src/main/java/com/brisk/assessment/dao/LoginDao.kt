package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.LoginRes

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: LoginRes)
}
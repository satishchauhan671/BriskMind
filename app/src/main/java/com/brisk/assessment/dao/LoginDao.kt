package com.brisk.assessment.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.LoginRes

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: LoginRes)

    @Query("select * from login_mst")
    fun getLoginDetails() : LiveData<LoginRes>

    @Query("delete from login_mst")
    fun deleteAll()
}
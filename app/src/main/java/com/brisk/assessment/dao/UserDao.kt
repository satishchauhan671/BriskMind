package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brisk.assessment.model.UserResponse

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userResponse: List<UserResponse>)


    @Query("Select * from user_data_mst")
    fun getUserData() : List<UserResponse>

    @Query("delete from user_data_mst")
    fun deleteAllUser()
}
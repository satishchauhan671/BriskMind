package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncBatchArray
import com.brisk.assessment.model.SyncUserAttendance
import com.brisk.assessment.model.SyncUserProfile

@Dao
interface SyncUserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncUserProfile: SyncUserProfile)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncUserProfileList: List<SyncUserProfile>)

}
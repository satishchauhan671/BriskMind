package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncUserPracticalAttendance

@Dao
interface SyncUserPracticalAttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncUserPracticalAttendance: List<SyncUserPracticalAttendance>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncUserVivaAttendance: SyncUserPracticalAttendance)
}
package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncAssessorAttendance

@Dao
interface SyncAssessorAttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncUserPracticalAttendance: List<SyncAssessorAttendance>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncUserVivaAttendance: SyncAssessorAttendance)
}
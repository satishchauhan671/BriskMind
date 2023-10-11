package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncUserTheoryAttendance

@Dao
interface SyncUserTheoryAttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncUserTheoryAttendance: List<SyncUserTheoryAttendance>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncUserTheoryAttendance: SyncUserTheoryAttendance)
}
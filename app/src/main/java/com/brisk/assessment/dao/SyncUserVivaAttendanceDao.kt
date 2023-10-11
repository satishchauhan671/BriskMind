package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncUserVivaAttendance

@Dao
interface SyncUserVivaAttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncUserVivaAttendance: List<SyncUserVivaAttendance>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncUserVivaAttendance: SyncUserVivaAttendance)
}
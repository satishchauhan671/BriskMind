package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncUserAttendance

@Dao
interface SyncUserAttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncBatchArray: List<SyncUserAttendance>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncBatchArray: SyncUserAttendance)
}
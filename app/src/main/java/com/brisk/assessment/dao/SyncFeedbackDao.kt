package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncFeedbackArray

@Dao
interface SyncFeedbackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncFeedbackArray: List<SyncFeedbackArray>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncFeedbackArray: SyncFeedbackArray)
}
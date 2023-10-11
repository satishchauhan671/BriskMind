package com.brisk.assessment.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.brisk.assessment.model.SyncUserArray

@Dao
interface SyncUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(syncFeedbackArray: List<SyncUserArray>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(syncFeedbackArray: SyncUserArray)
}
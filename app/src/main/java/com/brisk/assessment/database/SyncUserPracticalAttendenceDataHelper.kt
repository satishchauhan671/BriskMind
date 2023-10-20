package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.SyncUserPracticalAttendance
import com.brisk.assessment.model.SyncUserTheoryAttendance
import com.brisk.assessment.model.SyncUserVivaAttendance

class SyncUserPracticalAttendenceDataHelper {
    companion object {
        fun saveSyncUserPracticalAttData(syncUserPracticalAttendance: SyncUserPracticalAttendance, context: Context?): Boolean {
            var a = false
            try {
                val syncUserPracticalAttendenceDataSource = SyncUserPracticalAttendenceDataSource.instance
                a = syncUserPracticalAttendenceDataSource!!.saveSyncUserPracticalAttData(syncUserPracticalAttendance, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncUserPracticalAttendenceDataSource.instance!!.deleteAll(context)
        }

        fun getSyncUserPracticalAttendanceData(context: Context?): ArrayList<SyncUserPracticalAttendance> {
            return SyncUserPracticalAttendenceDataSource.instance!!.getSyncUserPracticalAttendanceData(context)
        }

        fun getSyncUserPracticalAttDataByBatchId(batchId : String , context: Context?): SyncUserPracticalAttendance? {
            return SyncUserPracticalAttendenceDataSource.instance!!.getSyncUserPracticalAttDataByBatchId(batchId,context)
        }
    }
}
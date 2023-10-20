package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.SyncUserAttendance
import com.brisk.assessment.model.SyncUserTheoryAttendance
import com.brisk.assessment.model.SyncUserVivaAttendance

class SyncUserVivaAttendenceDataHelper {
    companion object {
        fun saveSyncUserVivaAttData(syncUserVivaAttendance: SyncUserVivaAttendance, context: Context?): Boolean {
            var a = false
            try {
                val syncUserVivaAttendenceDataSource = SyncUserVivaAttendenceDataSource.instance
                a = syncUserVivaAttendenceDataSource!!.saveSyncUserVivaAttData(syncUserVivaAttendance, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncUserVivaAttendenceDataSource.instance!!.deleteAll(context)
        }

        fun getSyncUserVivaAttendanceData(context: Context?): ArrayList<SyncUserVivaAttendance> {
            return SyncUserVivaAttendenceDataSource.instance!!.getSyncUserVivaAttendanceData(context)
        }

        fun getSyncUserVivaAttDataByBatchId(batchId : String , context: Context?): SyncUserVivaAttendance? {
            return SyncUserVivaAttendenceDataSource.instance!!.getSyncUserVivaAttDataByBatchId(batchId,context)
        }
    }
}
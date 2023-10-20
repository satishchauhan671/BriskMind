package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.SyncUserTheoryAttendance

class SyncUserTheoryAttendenceDataHelper {
    companion object {
        fun saveSyncUserTheoryData(syncUserTheoryAttendance: SyncUserTheoryAttendance, context: Context?): Boolean {
            var a = false
            try {
                val syncUserTheoryAttendenceDataSource = SyncUserTheoryAttendenceDataSource.instance
                a = syncUserTheoryAttendenceDataSource!!.saveSyncUserTheoryData(syncUserTheoryAttendance, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncUserTheoryAttendenceDataSource.instance!!.deleteAll(context)
        }

        fun getSyncUserTheoryAttendanceData(context: Context?): ArrayList<SyncUserTheoryAttendance> {
            return SyncUserTheoryAttendenceDataSource.instance!!.getSyncUserTheoryAttendanceData(context)
        }
    }
}
package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.SyncAssessorAttendance
import com.brisk.assessment.model.SyncUserAttendance
import com.brisk.assessment.model.SyncUserTheoryAttendance
import com.brisk.assessment.model.SyncUserVivaAttendance

class SyncAssessorAttendenceDataHelper {
    companion object {
        fun saveSyncAssessorAttData(syncAssessorAttendance: SyncAssessorAttendance, context: Context?): Boolean {
            var a = false
            try {
                val syncAssessorAttendenceDataSource = SyncAssessorAttendenceDataSource.instance
                a = syncAssessorAttendenceDataSource!!.saveSyncAssessorAttData(syncAssessorAttendance, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncAssessorAttendenceDataSource.instance!!.deleteAll(context)
        }

        fun getSyncAssessorAttendanceData(context: Context?): ArrayList<SyncAssessorAttendance> {
            return SyncAssessorAttendenceDataSource.instance!!.getSyncAssessorAttendanceData(context)
        }

        fun getSyncAssessorAttendanceDataByBatchId(batchId : String , context: Context?): SyncAssessorAttendance? {
            return SyncAssessorAttendenceDataSource.instance!!.getSyncAssessorAttendanceDataByBatchId(batchId,context)
        }
    }
}
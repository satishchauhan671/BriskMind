package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SyncBatchArray
import com.brisk.assessment.model.SyncUserAttendance
import com.brisk.assessment.model.SyncUserTheoryAttendance

class SyncUserAttendenceDataHelper {
    companion object {
        fun saveSyncUserAttendenceData(syncUserAttendance: SyncUserAttendance, context: Context?): Boolean {
            var a = false
            try {
                val syncUserAttendanceDataSource = SyncUserAttendenceDataSource.instance
                a = syncUserAttendanceDataSource!!.saveSyncUserAttendenceData(syncUserAttendance, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncUserAttendenceDataSource.instance!!.deleteAll(context)
        }

        fun getSyncUserAttendanceData(context: Context?): ArrayList<SyncUserAttendance> {
            return SyncUserAttendenceDataSource.instance!!.getSyncUserAttendanceData(context)
        }
        fun getSyncUserAttendanceDataByBatchId(batchId : String , context: Context?): SyncUserAttendance? {
            return SyncUserAttendenceDataSource.instance!!.getSyncUserAttendanceDataByBatchId(batchId,context)
        }
    }
}
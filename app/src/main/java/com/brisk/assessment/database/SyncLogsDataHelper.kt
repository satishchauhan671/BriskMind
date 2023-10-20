package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SyncBatchArray
import com.brisk.assessment.model.SyncFeedbackArray
import com.brisk.assessment.model.SyncLogArray
import com.brisk.assessment.model.SyncPaperArray
import com.brisk.assessment.model.SyncUserProfile

class SyncLogsDataHelper {
    companion object {
        fun syncLogsData(syncLogs: SyncLogArray, context: Context?): Boolean {
            var a = false
            try {
                val syncLogsDataSource = SyncLogsDataSource.instance
                a = syncLogsDataSource!!.syncLogsData(syncLogs, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncLogsDataSource.instance!!.deleteAll(context)
        }

        fun getSyncLogsData(context: Context?): ArrayList<SyncLogArray> {
            return SyncLogsDataSource.instance!!.getSyncLogsData(context)
        }
    }
}
package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SyncBatchArray

class SyncBatchDataHelper {
    companion object {
        fun syncBatchData(syncBatchArray: SyncBatchArray, context: Context?): Boolean {
            var a = false
            try {
                val syncBatchDataSource = SyncBatchDataSource.instance
                a = syncBatchDataSource!!.syncBatchData(syncBatchArray, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncBatchDataSource.instance!!.deleteAll(context)
        }

        fun getSyncBatchData(context: Context?): ArrayList<SyncBatchArray> {
            return SyncBatchDataSource.instance!!.getSyncBatchData(context)
        }
    }
}
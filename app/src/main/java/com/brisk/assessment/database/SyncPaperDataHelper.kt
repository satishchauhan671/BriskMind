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
import com.brisk.assessment.model.SyncPaperArray
import com.brisk.assessment.model.SyncUserProfile

class SyncPaperDataHelper {
    companion object {
        fun syncPaperData(syncPaper: SyncPaperArray, context: Context?): Boolean {
            var a = false
            try {
                val syncPaperDataSource = SyncPaperDataSource.instance
                a = syncPaperDataSource!!.syncPaperData(syncPaper, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncPaperDataSource.instance!!.deleteAll(context)
        }

        fun getSyncPaperData(context: Context?): ArrayList<SyncPaperArray> {
            return SyncPaperDataSource.instance!!.getSyncPaperData(context)
        }
    }
}
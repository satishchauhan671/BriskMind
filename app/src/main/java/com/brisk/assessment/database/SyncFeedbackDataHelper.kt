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
import com.brisk.assessment.model.SyncUserProfile

class SyncFeedbackDataHelper {
    companion object {
        fun syncFeedbackData(syncFeedback: SyncFeedbackArray, context: Context?): Boolean {
            var a = false
            try {
                val syncFeedbackDataSource = SyncFeedbackDataSource.instance
                a = syncFeedbackDataSource!!.syncFeedbackData(syncFeedback, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncFeedbackDataSource.instance!!.deleteAll(context)
        }

        fun getSyncFeedbackData(context: Context?): ArrayList<SyncFeedbackArray> {
            return SyncFeedbackDataSource.instance!!.getSyncFeedbackData(context)
        }
    }
}
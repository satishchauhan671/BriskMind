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
import com.brisk.assessment.model.SyncUserArray
import com.brisk.assessment.model.SyncUserProfile

class SyncUserDataHelper {
    companion object {
        fun syncUserData(syncUser: SyncUserArray, context: Context?): Boolean {
            var a = false
            try {
                val syncUserDataSource = SyncUserDataSource.instance
                a = syncUserDataSource!!.syncUserData(syncUser, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncUserDataSource.instance!!.deleteAll(context)
        }

        fun getSyncUserData(context: Context?): ArrayList<SyncUserArray> {
            return SyncUserDataSource.instance!!.getSyncUserData(context)
        }
    }
}
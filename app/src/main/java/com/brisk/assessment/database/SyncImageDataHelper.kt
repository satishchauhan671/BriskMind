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
import com.brisk.assessment.model.SyncImageArray
import com.brisk.assessment.model.SyncPaperArray
import com.brisk.assessment.model.SyncUserProfile

class SyncImageDataHelper {
    companion object {
        fun syncImageData(syncImage: SyncImageArray, context: Context?): Boolean {
            var a = false
            try {
                val syncImageDataSource = SyncImageDataSource.instance
                a = syncImageDataSource!!.syncImageData(syncImage, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncImageDataSource.instance!!.deleteAll(context)
        }

        fun getSyncImageData(context: Context?): ArrayList<SyncImageArray> {
            return SyncImageDataSource.instance!!.getSyncImageData(context)
        }
    }
}
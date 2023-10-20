package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SyncBatchArray
import com.brisk.assessment.model.SyncUserProfile

class SyncUserProfileDataHelper {
    companion object {
        fun syncUserProfileData(syncUserProfile: SyncUserProfile, context: Context?): Boolean {
            var a = false
            try {
                val syncUserProfileDataSource = SyncUserProfileDataSource.instance
                a = syncUserProfileDataSource!!.syncUserProfileData(syncUserProfile, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SyncUserProfileDataSource.instance!!.deleteAll(context)
        }

        fun getSyncUserProfileData(context: Context?): ArrayList<SyncUserProfile> {
            return SyncUserProfileDataSource.instance!!.getSyncUserProfileData(context)
        }
    }
}
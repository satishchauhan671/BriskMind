package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes

class FeedbackDataHelper {
    companion object {
        fun saveFeedbackData(feedbackResponse: FeedbackResponse, context: Context?): Boolean {
            var a = false
            try {
                val feedbackDataSource = FeedbackDataSource.instance
                a = feedbackDataSource!!.saveFeedbackData(feedbackResponse, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            FeedbackDataSource.instance!!.deleteAll(context)
        }

        fun getFeedbackData(context: Context?): ArrayList<FeedbackResponse> {
            return FeedbackDataSource.instance!!.getFeedbackData(context)
        }
    }
}
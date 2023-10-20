package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SubQuestionResponse

class SubQuestionDataHelper {
    companion object {
        fun saveSubQuestionData(subQuestionResponse: SubQuestionResponse, context: Context?): Boolean {
            var a = false
            try {
                val subQuestionDataSource = SubQuestionDataSource.instance
                a = subQuestionDataSource!!.saveSubQuestionData(subQuestionResponse, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            SubQuestionDataSource.instance!!.deleteAll(context)
        }

        fun getSubQuestionData(context: Context?): SubQuestionResponse? {
            return SubQuestionDataSource.instance!!.getSubQuestionData(context)
        }

        fun getSubQuestionByQueId(questionId: String ,context: Context?): ArrayList<SubQuestionResponse> {
            return SubQuestionDataSource.instance!!.getSubQuestionByQueId(questionId,context)
        }
    }
}
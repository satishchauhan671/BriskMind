package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.UserResponse

class UserArrayDataHelper {
    companion object {
        fun saveUserArrayData(userResponse: UserResponse, context: Context?): Boolean {
            var a = false
            try {
                val userArrayDataSource = UserArrayDataSource.instance
                a = userArrayDataSource!!.saveUserArrayData(userResponse, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            UserArrayDataSource.instance!!.deleteAll(context)
        }

        fun getUserArrayData(context: Context?): UserResponse? {
            return UserArrayDataSource.instance!!.getUserArrayData(context)
        }

        fun getUserArrayDataByBatchId(batchId :  String ,context: Context?): ArrayList<UserResponse>? {
            return UserArrayDataSource.instance!!.getUserArrayDataByBatchId(batchId,context)
        }
    }
}
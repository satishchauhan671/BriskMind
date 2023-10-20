package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes

class LoginDataHelper {
    companion object {
        fun saveUserDatabaseData(loginResponseModel: LoginRes, context: Context?): Boolean {
            var a = false
            try {
                val ciLoginDataSource = LoginDataSource.instance
                a = ciLoginDataSource!!.saveUserDatabaseData(loginResponseModel, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            LoginDataSource.instance!!.deleteAll(context)
        }

        fun getLogin(context: Context?): LoginRes? {
            return LoginDataSource.instance!!.getLogin(context)
        }
    }
}
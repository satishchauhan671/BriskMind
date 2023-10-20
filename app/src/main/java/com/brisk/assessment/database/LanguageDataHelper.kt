package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes

class LanguageDataHelper {
    companion object {
        fun saveLanguageData(languageRes: LanguageRes, context: Context?): Boolean {
            var a = false
            try {
                val languageDataSource = LanguageDataSource.instance
                a = languageDataSource!!.saveLanguageData(languageRes, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            LanguageDataSource.instance!!.deleteAll(context)
        }

        fun getLanguage(context: Context?): LanguageRes? {
            return LanguageDataSource.instance!!.getLanguage(context)
        }
    }
}
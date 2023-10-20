package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.TextsRes

class LanguageTextDataHelper {
    companion object {
        fun saveLanguageTextData(textsRes: TextsRes, context: Context?): Boolean {
            var a = false
            try {
                val languageTextDataSource = LanguageTextDataSource.instance
                a = languageTextDataSource!!.saveLanguageTextData(textsRes, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            LanguageTextDataSource.instance!!.deleteAll(context)
        }

        fun getLanguageText(context: Context?): TextsRes? {
            return LanguageTextDataSource.instance!!.getLanguageText(context)
        }
    }
}
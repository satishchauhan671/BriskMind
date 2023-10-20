package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes

class ImportLanguageDataHelper {
    companion object {
        fun saveImportLanguageData(importLanguageRes : ImportLanguageResponse, context: Context?): Boolean {
            var a = false
            try {
                val importLanuageDataSource = ImportLanuageDataSource.instance
                a = importLanuageDataSource!!.saveImportLanguageData(importLanguageRes, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            ImportLanuageDataSource.instance!!.deleteAll(context)
        }

        fun getImportLanguageData(context: Context?): ArrayList<ImportLanguageResponse> {
            return ImportLanuageDataSource.instance!!.getImportLanguageData(context)
        }
    }
}
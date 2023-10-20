package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.TransOptionRes

class TransOptionDataHelper {
    companion object {
        fun saveOptionArrayData(optionRes: TransOptionRes, context: Context?): Boolean {
            var a = false
            try {
                val transOptionDataSource = TransOptionDataSource.instance
                a = transOptionDataSource!!.saveOptionArrayData(optionRes, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            TransOptionDataSource.instance!!.deleteAll(context)
        }

        fun getOptionArrayData(context: Context?): TransOptionRes? {
            return TransOptionDataSource.instance!!.getOptionArrayData(context)
        }
    }
}
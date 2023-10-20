package com.brisk.assessment.database

import android.content.Context
import android.content.LocusId
import com.brisk.assessment.model.OptionRes

class OptionArrayDataHelper {
    companion object {
        fun saveOptionArrayData(optionRes: OptionRes, context: Context?): Boolean {
            var a = false
            try {
                val optionArrayDataSource = OptionArrayDataSource.instance
                a = optionArrayDataSource!!.saveOptionArrayData(optionRes, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            OptionArrayDataSource.instance!!.deleteAll(context)
        }

        fun getOptionArrayData(sqId: String ,context: Context?): ArrayList<OptionRes> {
            return OptionArrayDataSource.instance!!.getOptionArrayData(sqId,context)
        }
    }
}
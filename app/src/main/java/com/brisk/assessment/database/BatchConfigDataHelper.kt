package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.BatchConfigRes
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes

class BatchConfigDataHelper {
    companion object {
        fun saveBatchConfigData(batchConfigRes: BatchConfigRes, context: Context?): Boolean {
            var a = false
            try {
                val batchConfigDataSource = BatchConfigDataSource.instance
                a = batchConfigDataSource!!.saveBatchConfigData(batchConfigRes, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            BatchConfigDataSource.instance!!.deleteAll(context)
        }

        fun getBatchConfigDataByBatchId(batchId: String,context: Context?): BatchConfigRes? {
            return BatchConfigDataSource.instance!!.getBatchConfigDataByBatchId(batchId,context)
        }
    }
}
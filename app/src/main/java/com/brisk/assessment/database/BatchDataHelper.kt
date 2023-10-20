package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes

class BatchDataHelper {
    companion object {
        fun saveBatchData(batchRes: BatchRes, context: Context?): Boolean {
            var a = false
            try {
                val batchDataSource = BatchDataSource.instance
                a = batchDataSource!!.saveBatchData(batchRes, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            BatchDataSource.instance!!.deleteAll(context)
        }

        fun getBatchData(context: Context?): ArrayList<BatchRes>? {
            return BatchDataSource.instance!!.getBatchData(context)
        }

        fun getBatchByBatchId(batchId : String , context: Context?): BatchRes? {
            return BatchDataSource.instance!!.getBatchByBatchId(batchId,context)
        }
    }
}
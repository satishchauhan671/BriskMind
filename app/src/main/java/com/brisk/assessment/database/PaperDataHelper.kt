package com.brisk.assessment.database

import android.content.Context
import com.brisk.assessment.model.PaperResponse

class PaperDataHelper {
    companion object {
        fun savePaperData(paperResponse: PaperResponse, context: Context?): Boolean {
            var a = false
            try {
                val paperDataSource = PaperDataSource.instance
                a = paperDataSource!!.savePaperData(paperResponse, context)
            } catch (et: Exception) {
                et.printStackTrace()
            }
            return a
        }

        fun deleteAll(context: Context?) {
            PaperDataSource.instance!!.deleteAll(context)
        }

        fun getPaperData(context: Context?): PaperResponse? {
            return PaperDataSource.instance!!.getPaperData(context)
        }

        fun getPaperListByPaperSetId(paperSetId : String , context: Context?): ArrayList<PaperResponse> {
            return PaperDataSource.instance!!.getPaperListByPaperSetId(paperSetId,context)
        }
    }
}
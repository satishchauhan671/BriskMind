package com.brisk.assessment.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.brisk.assessment.database.DatabaseHelper.Companion.getInstance
import com.brisk.assessment.database.DatabaseHelper.Companion.languages_mst
import com.brisk.assessment.database.DatabaseHelper.Companion.lock
import com.brisk.assessment.database.DatabaseHelper.Companion.login_mst
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.PaperResponse

class PaperDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: PaperDataSource? = null
        private val TAG = PaperDataSource::class.java.simpleName
        val instance: PaperDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = PaperDataSource()
                myDataSource
            }

        fun getSQLiteDb(isWritable: Boolean, context: Context?): SQLiteDatabase {
            val databaseHelper = getInstance(context!!)
            return if (isWritable) {
                databaseHelper!!.writableDatabase
            } else {
                databaseHelper!!.readableDatabase
            }
        }
    }

    fun savePaperData(paperResponse: PaperResponse, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.paper_set_id, paperResponse.paper_set_id)
                values.put(DatabaseHelper.question_id, paperResponse.question_id)
                values.put(DatabaseHelper.question, paperResponse.question)
                values.put(DatabaseHelper.question_marks, paperResponse.question_marks)
                values.put(DatabaseHelper.q_image, paperResponse.q_image)
                values.put(DatabaseHelper.sub_question_count, paperResponse.sub_question_count)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.paper_mst,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE
                )

                if (l > 0) a = true
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return a
        }
    }
    private fun cursorData(cursor: Cursor): PaperResponse {
        val paperResponse = PaperResponse()
        paperResponse.paper_set_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.paper_set_id))
        paperResponse.question_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.question_id))
        paperResponse.question = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.question))
        paperResponse.question_marks = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.question_marks))
        paperResponse.q_image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.q_image))
        paperResponse.sub_question_count = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.sub_question_count))

        return paperResponse
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.paper_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getPaperData(context: Context?): PaperResponse? {
        var paperResponse: PaperResponse? = null
        synchronized(lock) {
            val db: SQLiteDatabase = getSQLiteDb(false, context)
            try {
                val query = "SELECT * FROM ${DatabaseHelper.paper_mst}"
                val cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) paperResponse = cursorData(cursor)
                cursor.close()
                db.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
        }
        return paperResponse
    }

    fun getPaperListByPaperSetId(paperSetId : String , context: Context?): ArrayList<PaperResponse> {
        synchronized(lock) {
            val batchRes : ArrayList<PaperResponse> = ArrayList<PaperResponse>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM " + DatabaseHelper.paper_mst + " WHERE " + DatabaseHelper.paper_set_id + " = '" + paperSetId + "'",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        batchRes.add(cursorData(cursor))
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return batchRes
        }
    }
}
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
import com.brisk.assessment.model.SubQuestionResponse

class SubQuestionDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SubQuestionDataSource? = null
        private val TAG = SubQuestionDataSource::class.java.simpleName
        val instance: SubQuestionDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SubQuestionDataSource()
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

    fun saveSubQuestionData(subQuestionResponse: SubQuestionResponse, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.question_id, subQuestionResponse.question_id)
                values.put(DatabaseHelper.squestion_id, subQuestionResponse.squestion_id)
                values.put(DatabaseHelper.question, subQuestionResponse.question)
                values.put(DatabaseHelper.question_marks, subQuestionResponse.question_marks)
                values.put(DatabaseHelper.seq_no, subQuestionResponse.seq_no)
                values.put(DatabaseHelper.trans_question, subQuestionResponse.trans_question)
                values.put(DatabaseHelper.option_count, subQuestionResponse.option_count)
                values.put(DatabaseHelper.q_image, subQuestionResponse.q_image)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.sub_question_mst,
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
    private fun cursorData(cursor: Cursor): SubQuestionResponse {
        val subQuestionResponse = SubQuestionResponse()
        subQuestionResponse.question_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.question_id))
        subQuestionResponse.squestion_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.squestion_id))
        subQuestionResponse.question = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.question))
        subQuestionResponse.question_marks = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.question_marks))
        subQuestionResponse.seq_no = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.seq_no))
        subQuestionResponse.trans_question = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.trans_question))
        subQuestionResponse.option_count = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.option_count))
        subQuestionResponse.q_image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.q_image))

        return subQuestionResponse
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sub_question_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSubQuestionData(context: Context?): SubQuestionResponse? {
        var subQuestionResponse: SubQuestionResponse? = null
        synchronized(lock) {
            val db: SQLiteDatabase = getSQLiteDb(false, context)
            try {
                val query = "SELECT * FROM ${DatabaseHelper.sub_question_mst}"
                val cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) subQuestionResponse = cursorData(cursor)
                cursor.close()
                db.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
        }
        return subQuestionResponse
    }

    fun getSubQuestionByQueId(questionId : String ,context: Context?): ArrayList<SubQuestionResponse> {
        synchronized(lock) {
            val subQuestionResponse : ArrayList<SubQuestionResponse> = ArrayList<SubQuestionResponse>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM " + DatabaseHelper.sub_question_mst + " WHERE " + DatabaseHelper.question_id + " = '" + questionId + "'",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        subQuestionResponse.add(cursorData(cursor))
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return subQuestionResponse
        }
    }
}
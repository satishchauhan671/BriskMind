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

class FeedbackDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: FeedbackDataSource? = null
        private val TAG = FeedbackDataSource::class.java.simpleName
        val instance: FeedbackDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = FeedbackDataSource()
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

    fun saveFeedbackData(feedbackResponse: FeedbackResponse, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.fq_id, feedbackResponse.fq_id)
                values.put(DatabaseHelper.fq_text, feedbackResponse.fq_text)
                values.put(DatabaseHelper.fq_type, feedbackResponse.fq_type)
                values.put(DatabaseHelper.user_type, feedbackResponse.user_type)
                values.put(DatabaseHelper.option1, feedbackResponse.option1)
                values.put(DatabaseHelper.option2, feedbackResponse.option2)
                values.put(DatabaseHelper.option3, feedbackResponse.option3)
                values.put(DatabaseHelper.option4, feedbackResponse.option4)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.feedback_mst,
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
    private fun cursorData(cursor: Cursor): FeedbackResponse {
        val feedbackResponse = FeedbackResponse()
        feedbackResponse.fq_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.fq_id))
        feedbackResponse.fq_text = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.fq_text))
        feedbackResponse.fq_type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.fq_type))
        feedbackResponse.user_type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_type))
        feedbackResponse.option1 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.option1))
        feedbackResponse.option2 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.option2))
        feedbackResponse.option3 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.option3))
        feedbackResponse.option4 = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.option4))

        return feedbackResponse
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.feedback_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getFeedbackData(context: Context?): ArrayList<FeedbackResponse> {
        synchronized(lock) {
            val feedbackResponse : ArrayList<FeedbackResponse> = ArrayList<FeedbackResponse>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.feedback_mst}",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        feedbackResponse.add(cursorData(cursor))
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return feedbackResponse
        }
    }
}
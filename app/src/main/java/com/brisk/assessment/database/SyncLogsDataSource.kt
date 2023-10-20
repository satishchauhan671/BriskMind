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
import com.brisk.assessment.model.SyncBatchArray
import com.brisk.assessment.model.SyncFeedbackArray
import com.brisk.assessment.model.SyncLogArray
import com.brisk.assessment.model.SyncPaperArray
import com.brisk.assessment.model.SyncUserProfile

class SyncLogsDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SyncLogsDataSource? = null
        private val TAG = SyncLogsDataSource::class.java.simpleName
        val instance: SyncLogsDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SyncLogsDataSource()
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

    fun syncLogsData(syncLogsArray: SyncLogArray, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.batch_id, syncLogsArray.batch_id)
                values.put(DatabaseHelper.created_at, syncLogsArray.created_at)
                values.put(DatabaseHelper.lat, syncLogsArray.lat)
                values.put(DatabaseHelper.long, syncLogsArray.long)
                values.put(DatabaseHelper.log_text, syncLogsArray.log_text)
                values.put(DatabaseHelper.paper_set_id, syncLogsArray.paper_set_id)
                values.put(DatabaseHelper.paper_type_id, syncLogsArray.paper_type_id)
                values.put(DatabaseHelper.question_id, syncLogsArray.question_id)
                values.put(DatabaseHelper.squestion_id, syncLogsArray.squestion_id)
                values.put(DatabaseHelper.user_id, syncLogsArray.user_id)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.sync_log_arr_mst,
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

    private fun cursorData(cursor: Cursor): SyncLogArray {
        val syncLogsArray = SyncLogArray()
        syncLogsArray.batch_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        syncLogsArray.created_at =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.created_at))
        syncLogsArray.lat = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.lat))
        syncLogsArray.long = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.long))
        syncLogsArray.log_text =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.log_text))
        syncLogsArray.paper_set_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.paper_set_id))
        syncLogsArray.paper_type_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.paper_type_id))
        syncLogsArray.question_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.question_id))
        syncLogsArray.squestion_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.squestion_id))
        syncLogsArray.user_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))

        return syncLogsArray
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sync_log_arr_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSyncLogsData(context: Context?): ArrayList<SyncLogArray> {
        synchronized(lock) {
            val feedbackResponse: ArrayList<SyncLogArray> = ArrayList<SyncLogArray>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.sync_log_arr_mst}",
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
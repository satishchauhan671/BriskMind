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
import com.brisk.assessment.model.SyncPaperArray
import com.brisk.assessment.model.SyncUserArray
import com.brisk.assessment.model.SyncUserProfile

class SyncUserDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SyncUserDataSource? = null
        private val TAG = SyncUserDataSource::class.java.simpleName
        val instance: SyncUserDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SyncUserDataSource()
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

    fun syncUserData(syncUserArray: SyncUserArray, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.attendance_status, syncUserArray.attendance_status)
                values.put(DatabaseHelper.batch_id, syncUserArray.batch_id)
                values.put(DatabaseHelper.consumed_time, syncUserArray.consumed_time)
                values.put(DatabaseHelper.paper_set_id, syncUserArray.paper_set_id)
                values.put(DatabaseHelper.paper_type_id, syncUserArray.paper_type_id)
                values.put(DatabaseHelper.status, syncUserArray.status)
                values.put(DatabaseHelper.user_end_time, syncUserArray.user_end_time)
                values.put(DatabaseHelper.user_id, syncUserArray.user_id)
                values.put(DatabaseHelper.user_start_time, syncUserArray.user_start_time)
                values.put(DatabaseHelper.video_url, syncUserArray.video_url)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.sync_user_arr_mst,
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
    private fun cursorData(cursor: Cursor): SyncUserArray {
        val syncUserArray = SyncUserArray()
        syncUserArray.attendance_status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.attendance_status))
        syncUserArray.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        syncUserArray.consumed_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.consumed_time))
        syncUserArray.paper_set_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.paper_set_id))
        syncUserArray.paper_type_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.paper_type_id))
        syncUserArray.status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.status))
        syncUserArray.user_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))
        syncUserArray.user_end_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_end_time))
        syncUserArray.user_start_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_start_time))
        syncUserArray.video_url = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.video_url))

        return syncUserArray
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sync_user_arr_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSyncUserData(context: Context?): ArrayList<SyncUserArray> {
        synchronized(lock) {
            val feedbackResponse : ArrayList<SyncUserArray> = ArrayList<SyncUserArray>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.sync_user_arr_mst}",
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
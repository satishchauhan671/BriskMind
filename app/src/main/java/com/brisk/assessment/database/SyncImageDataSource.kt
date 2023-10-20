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
import com.brisk.assessment.model.SyncImageArray
import com.brisk.assessment.model.SyncPaperArray
import com.brisk.assessment.model.SyncUserProfile

class SyncImageDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SyncImageDataSource? = null
        private val TAG = SyncImageDataSource::class.java.simpleName
        val instance: SyncImageDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SyncImageDataSource()
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

    fun syncImageData(syncImageArray: SyncImageArray, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.batch_id, syncImageArray.batch_id)
                values.put(DatabaseHelper.captured_in, syncImageArray.captured_in)
                values.put(DatabaseHelper.capture_time, syncImageArray.capture_time)
                values.put(DatabaseHelper.image_url, syncImageArray.image_url)
                values.put(DatabaseHelper.lat, syncImageArray.lat)
                values.put(DatabaseHelper.long, syncImageArray.long)
                values.put(DatabaseHelper.user_id, syncImageArray.user_id)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.sync_image_arr_mst,
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
    private fun cursorData(cursor: Cursor): SyncImageArray {
        val syncImageArray = SyncImageArray()
        syncImageArray.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        syncImageArray.captured_in = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.captured_in))
        syncImageArray.capture_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.capture_time))
        syncImageArray.image_url = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.image_url))
        syncImageArray.lat = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.lat))
        syncImageArray.long = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.long))
        syncImageArray.user_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))

        return syncImageArray
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sync_image_arr_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSyncImageData(context: Context?): ArrayList<SyncImageArray> {
        synchronized(lock) {
            val feedbackResponse : ArrayList<SyncImageArray> = ArrayList<SyncImageArray>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.sync_image_arr_mst}",
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
package com.brisk.assessment.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.brisk.assessment.database.DatabaseHelper.Companion.getInstance
import com.brisk.assessment.database.DatabaseHelper.Companion.id
import com.brisk.assessment.database.DatabaseHelper.Companion.languages_mst
import com.brisk.assessment.database.DatabaseHelper.Companion.lock
import com.brisk.assessment.database.DatabaseHelper.Companion.login_mst
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SyncBatchArray

class SyncBatchDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SyncBatchDataSource? = null
        private val TAG = SyncBatchDataSource::class.java.simpleName
        val instance: SyncBatchDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SyncBatchDataSource()
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

    fun syncBatchData(syncBatchArray: SyncBatchArray, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.batch_id, syncBatchArray.batch_id)
                values.put(DatabaseHelper.device_id, syncBatchArray.device_id)
                values.put(DatabaseHelper.exported_by, syncBatchArray.exported_by)
                values.put(DatabaseHelper.exported_user_type, syncBatchArray.exported_user_type)
                values.put(DatabaseHelper.export_time, syncBatchArray.export_time)

                var l: Long = -1
                if (syncBatchArray.batch_id != null) {
                    val syncBatchModel1 =
                        getByBatchId(syncBatchArray.batch_id!!, context)
                    val db = getSQLiteDb(true, context)
                    l =
                        if (syncBatchModel1 == null)
                            db.insertWithOnConflict(
                                DatabaseHelper.sync_batch_arr_mst,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_REPLACE
                            ) else
                            db.update(
                                DatabaseHelper.sync_batch_arr_mst,
                                values,
                                "$id=?",
                                arrayOf(syncBatchModel1.id.toString() + "")
                            ).toLong()
                }
                else {
                    val db = getSQLiteDb(true, context)
                    l = db.insertWithOnConflict(
                        DatabaseHelper.sync_batch_arr_mst,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE
                    )
                }

                if (l > 0) a = true
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return a
        }
    }


    private val queryData: Array<String>
        get() = arrayOf(
            id,
            DatabaseHelper.batch_id,
            DatabaseHelper.device_id,
            DatabaseHelper.exported_by,
            DatabaseHelper.exported_user_type,
            DatabaseHelper.export_time,
            DatabaseHelper.export_type
        )


    private fun cursorData(cursor: Cursor): SyncBatchArray {
        val syncBatchArray = SyncBatchArray()
        syncBatchArray.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        syncBatchArray.device_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.device_id))
        syncBatchArray.exported_by = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exported_by))
        syncBatchArray.exported_user_type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exported_user_type))
        syncBatchArray.export_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.export_time))
        syncBatchArray.export_type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.export_type))

        return syncBatchArray
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sync_batch_arr_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSyncBatchData(context: Context?): ArrayList<SyncBatchArray> {
        synchronized(lock) {
            val feedbackResponse : ArrayList<SyncBatchArray> = ArrayList<SyncBatchArray>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.sync_batch_arr_mst}",
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


    fun getByBatchId(batchId: String, context: Context?): SyncBatchArray? {
        synchronized(lock) {
            var syncBatchArray: SyncBatchArray? = null
            val db = getSQLiteDb(false, context)
            try {
                val cursor = db.query(
                    DatabaseHelper.sync_batch_arr_mst,
                    queryData,
                    DatabaseHelper.batch_id + "=?",
                    arrayOf(batchId),
                    null,
                    null,
                    null
                )
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    syncBatchArray = cursorData(cursor)
                }
                cursor.close()
                //db.close;
            } catch (se: android.database.SQLException) {
                se.printStackTrace()
            }
            return syncBatchArray
        }
    }


}
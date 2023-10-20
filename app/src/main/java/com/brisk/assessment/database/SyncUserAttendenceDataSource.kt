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
import com.brisk.assessment.model.SyncUserAttendance
import com.brisk.assessment.model.SyncUserTheoryAttendance

class SyncUserAttendenceDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SyncUserAttendenceDataSource? = null
        private val TAG = SyncUserAttendenceDataSource::class.java.simpleName
        val instance: SyncUserAttendenceDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SyncUserAttendenceDataSource()
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

    fun saveSyncUserAttendenceData(syncUserAttendance: SyncUserAttendance, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.batch_id, syncUserAttendance.batch_id)
                values.put(DatabaseHelper.entry_id, syncUserAttendance.entry_id)
                values.put(DatabaseHelper.entry_id_time, syncUserAttendance.entry_id_time)
                values.put(DatabaseHelper.entry_photo, syncUserAttendance.entry_photo)
                values.put(DatabaseHelper.entry_photo_time, syncUserAttendance.entry_photo_time)
                values.put(DatabaseHelper.exit_id, syncUserAttendance.exit_id)
                values.put(DatabaseHelper.exit_id_time, syncUserAttendance.exit_id_time)
                values.put(DatabaseHelper.exit_photo, syncUserAttendance.exit_photo)
                values.put(DatabaseHelper.exit_photo_time, syncUserAttendance.exit_photo_time)
                values.put(DatabaseHelper.user_id, syncUserAttendance.user_id)

                var l: Long = -1
                val db = getSQLiteDb(true, context)
                if (syncUserAttendance.batch_id != null) {
                    val syncBatchModel1 =
                        getByBatchId(syncUserAttendance.batch_id!!, context)
                    l =
                        if (syncBatchModel1 == null)
                            db.insertWithOnConflict(
                                DatabaseHelper.sync_user_attendance_mst,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_REPLACE
                            ) else
                            db.update(
                                DatabaseHelper.sync_user_attendance_mst,
                                values,
                                "${DatabaseHelper.batch_id}=?",
                                arrayOf(syncBatchModel1.batch_id.toString() + "")
                            ).toLong()
                }
                else {
                    val db = getSQLiteDb(true, context)
                    l = db.insertWithOnConflict(
                        DatabaseHelper.sync_user_attendance_mst,
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
            DatabaseHelper.id,
            DatabaseHelper.batch_id,
            DatabaseHelper.entry_id,
            DatabaseHelper.entry_id_time,
            DatabaseHelper.entry_photo,
            DatabaseHelper.entry_photo_time,
            DatabaseHelper.exit_id,
            DatabaseHelper.exit_id_time,
            DatabaseHelper.exit_photo,
            DatabaseHelper.exit_photo_time,
            DatabaseHelper.user_id
        )



    private fun cursorData(cursor: Cursor): SyncUserAttendance {
        val syncUserAttendance = SyncUserAttendance()
        syncUserAttendance.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        syncUserAttendance.entry_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_id))
        syncUserAttendance.entry_id_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_id_time))
        syncUserAttendance.entry_photo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_photo))
        syncUserAttendance.entry_photo_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_photo_time))
        syncUserAttendance.exit_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_id))
        syncUserAttendance.exit_id_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_id_time))
        syncUserAttendance.exit_photo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_photo))
        syncUserAttendance.exit_photo_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_photo_time))
        syncUserAttendance.user_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))

        return syncUserAttendance
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sync_user_attendance_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSyncUserAttendanceData(context: Context?): ArrayList<SyncUserAttendance> {
        synchronized(lock) {
            val feedbackResponse : ArrayList<SyncUserAttendance> = ArrayList<SyncUserAttendance>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.sync_user_attendance_mst}",
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

    fun getSyncUserAttendanceDataByBatchId(batchId : String ,context: Context?): SyncUserAttendance? {
        synchronized(lock) {
            var syncUserAttendance : SyncUserAttendance ?= null
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM " + DatabaseHelper.sync_user_attendance_mst + " WHERE " + DatabaseHelper.batch_id + " = '" + batchId + "'",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        syncUserAttendance=cursorData(cursor)
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return syncUserAttendance
        }
    }

    fun getByBatchId(batchId: String, context: Context?): SyncUserAttendance? {
        synchronized(lock) {
            var syncUserAttendance: SyncUserAttendance? = null
            val db = getSQLiteDb(false, context)
            try {
                val cursor = db.query(
                    DatabaseHelper.sync_user_attendance_mst,
                    queryData,
                    DatabaseHelper.batch_id + "=?",
                    arrayOf(batchId),
                    null,
                    null,
                    null
                )
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    syncUserAttendance = cursorData(cursor)
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return syncUserAttendance
        }
    }
}
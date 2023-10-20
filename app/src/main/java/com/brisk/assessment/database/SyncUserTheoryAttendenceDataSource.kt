package com.brisk.assessment.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.brisk.assessment.database.DatabaseHelper.Companion.getInstance
import com.brisk.assessment.database.DatabaseHelper.Companion.lock
import com.brisk.assessment.model.SyncUserTheoryAttendance

class SyncUserTheoryAttendenceDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SyncUserTheoryAttendenceDataSource? = null
        private val TAG = SyncUserTheoryAttendenceDataSource::class.java.simpleName
        val instance: SyncUserTheoryAttendenceDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SyncUserTheoryAttendenceDataSource()
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

    fun saveSyncUserTheoryData(syncUserTheoryAttendance: SyncUserTheoryAttendance, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.batch_id, syncUserTheoryAttendance.batch_id)
                values.put(DatabaseHelper.entry_id, syncUserTheoryAttendance.entry_id)
                values.put(DatabaseHelper.entry_id_time, syncUserTheoryAttendance.entry_id_time)
                values.put(DatabaseHelper.entry_photo, syncUserTheoryAttendance.entry_photo)
                values.put(DatabaseHelper.entry_photo_time, syncUserTheoryAttendance.entry_photo_time)
                values.put(DatabaseHelper.exit_id, syncUserTheoryAttendance.exit_id)
                values.put(DatabaseHelper.exit_id_time, syncUserTheoryAttendance.exit_id_time)
                values.put(DatabaseHelper.exit_photo, syncUserTheoryAttendance.exit_photo)
                values.put(DatabaseHelper.exit_photo_time, syncUserTheoryAttendance.exit_photo_time)
                values.put(DatabaseHelper.user_id, syncUserTheoryAttendance.user_id)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.sync_user_theory_attendance_mst,
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
    private fun cursorData(cursor: Cursor): SyncUserTheoryAttendance {
        val syncUserTheoryAttendance = SyncUserTheoryAttendance()
        syncUserTheoryAttendance.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        syncUserTheoryAttendance.entry_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_id))
        syncUserTheoryAttendance.entry_id_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_id_time))
        syncUserTheoryAttendance.entry_photo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_photo))
        syncUserTheoryAttendance.entry_photo_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_photo_time))
        syncUserTheoryAttendance.exit_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_id))
        syncUserTheoryAttendance.exit_id_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_id_time))
        syncUserTheoryAttendance.exit_photo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_photo))
        syncUserTheoryAttendance.exit_photo_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_photo_time))
        syncUserTheoryAttendance.user_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))

        return syncUserTheoryAttendance
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sync_user_theory_attendance_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSyncUserTheoryAttendanceData(context: Context?): ArrayList<SyncUserTheoryAttendance> {
        synchronized(lock) {
            val feedbackResponse : ArrayList<SyncUserTheoryAttendance> = ArrayList<SyncUserTheoryAttendance>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.sync_user_theory_attendance_mst}",
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
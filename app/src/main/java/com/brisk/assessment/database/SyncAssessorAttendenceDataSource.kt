package com.brisk.assessment.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.brisk.assessment.database.DatabaseHelper.Companion.getInstance
import com.brisk.assessment.database.DatabaseHelper.Companion.lock
import com.brisk.assessment.model.SyncAssessorAttendance
import com.brisk.assessment.model.SyncUserAttendance
import com.brisk.assessment.model.SyncUserTheoryAttendance
import com.brisk.assessment.model.SyncUserVivaAttendance

class SyncAssessorAttendenceDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SyncAssessorAttendenceDataSource? = null
        private val TAG = SyncAssessorAttendenceDataSource::class.java.simpleName
        val instance: SyncAssessorAttendenceDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SyncAssessorAttendenceDataSource()
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

    fun saveSyncAssessorAttData(syncAssessorAttendance: SyncAssessorAttendance, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.batch_id, syncAssessorAttendance.batch_id)
                values.put(DatabaseHelper.entry_id, syncAssessorAttendance.entry_id)
                values.put(DatabaseHelper.entry_id_time, syncAssessorAttendance.entry_id_time)
                values.put(DatabaseHelper.entry_photo, syncAssessorAttendance.entry_photo)
                values.put(DatabaseHelper.entry_photo_time, syncAssessorAttendance.entry_photo_time)
                values.put(DatabaseHelper.exit_id, syncAssessorAttendance.exit_id)
                values.put(DatabaseHelper.exit_id_time, syncAssessorAttendance.exit_id_time)
                values.put(DatabaseHelper.exit_photo, syncAssessorAttendance.exit_photo)
                values.put(DatabaseHelper.exit_photo_time, syncAssessorAttendance.exit_photo_time)
                values.put(DatabaseHelper.user_id, syncAssessorAttendance.user_id)

                var l: Long = -1
                val db = getSQLiteDb(true, context)
                if (syncAssessorAttendance.batch_id != null) {
                    val syncBatchModel1 =
                        getByBatchId(syncAssessorAttendance.batch_id!!, context)
                    l =
                        if (syncBatchModel1 == null)
                            db.insertWithOnConflict(
                                DatabaseHelper.sync_assessor_attendance_mst,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_REPLACE
                            ) else
                            db.update(
                                DatabaseHelper.sync_assessor_attendance_mst,
                                values,
                                "${DatabaseHelper.batch_id}=?",
                                arrayOf(syncBatchModel1.batch_id.toString() + "")
                            ).toLong()
                }
                else {
                    val db = SyncUserAttendenceDataSource.getSQLiteDb(true, context)
                    l = db.insertWithOnConflict(
                        DatabaseHelper.sync_assessor_attendance_mst,
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



    private fun cursorData(cursor: Cursor): SyncAssessorAttendance {
        val syncAssessorAttendance = SyncAssessorAttendance()
        syncAssessorAttendance.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        syncAssessorAttendance.entry_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_id))
        syncAssessorAttendance.entry_id_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_id_time))
        syncAssessorAttendance.entry_photo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_photo))
        syncAssessorAttendance.entry_photo_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.entry_photo_time))
        syncAssessorAttendance.exit_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_id))
        syncAssessorAttendance.exit_id_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_id_time))
        syncAssessorAttendance.exit_photo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_photo))
        syncAssessorAttendance.exit_photo_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.exit_photo_time))
        syncAssessorAttendance.user_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))

        return syncAssessorAttendance
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sync_assessor_attendance_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSyncAssessorAttendanceData(context: Context?): ArrayList<SyncAssessorAttendance> {
        synchronized(lock) {
            val feedbackResponse : ArrayList<SyncAssessorAttendance> = ArrayList<SyncAssessorAttendance>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.sync_assessor_attendance_mst}",
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

    fun getSyncAssessorAttendanceDataByBatchId(batchId : String ,context: Context?): SyncAssessorAttendance? {
        synchronized(lock) {
            var syncAssessorAttendance : SyncAssessorAttendance?= null
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM " + DatabaseHelper.sync_assessor_attendance_mst + " WHERE " + DatabaseHelper.batch_id + " = '" + batchId + "'",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        syncAssessorAttendance=cursorData(cursor)
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return syncAssessorAttendance
        }
    }

    fun getByBatchId(batchId: String, context: Context?): SyncAssessorAttendance? {
        synchronized(lock) {
            var syncAssessorAttendance: SyncAssessorAttendance? = null
            val db = SyncUserAttendenceDataSource.getSQLiteDb(false, context)
            try {
                val cursor = db.query(
                    DatabaseHelper.sync_assessor_attendance_mst,
                    queryData,
                    DatabaseHelper.batch_id + "=?",
                    arrayOf(batchId),
                    null,
                    null,
                    null
                )
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    syncAssessorAttendance = cursorData(cursor)
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return syncAssessorAttendance
        }
    }
}
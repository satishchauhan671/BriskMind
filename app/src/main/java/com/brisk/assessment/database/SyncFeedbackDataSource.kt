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
import com.brisk.assessment.model.SyncAssessorAttendance
import com.brisk.assessment.model.SyncBatchArray
import com.brisk.assessment.model.SyncFeedbackArray
import com.brisk.assessment.model.SyncUserProfile

class SyncFeedbackDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SyncFeedbackDataSource? = null
        private val TAG = SyncFeedbackDataSource::class.java.simpleName
        val instance: SyncFeedbackDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SyncFeedbackDataSource()
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

    fun syncFeedbackData(syncFeedbackArray: SyncFeedbackArray, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.batch_id, syncFeedbackArray.batch_id)
                values.put(DatabaseHelper.created_at, syncFeedbackArray.created_at)
                values.put(DatabaseHelper.feedback_q_id, syncFeedbackArray.feedback_q_id)
                values.put(DatabaseHelper.response, syncFeedbackArray.response)
                values.put(DatabaseHelper.user_id, syncFeedbackArray.user_id)
                values.put(DatabaseHelper.user_type, syncFeedbackArray.user_type)

                var l: Long = -1
                val db = getSQLiteDb(true, context)
                if (syncFeedbackArray.batch_id != null) {
                    val syncBatchModel1 =
                        getByBatchIdFqId(
                            syncFeedbackArray.batch_id!!,
                            syncFeedbackArray.feedback_q_id!!,
                            context
                        )
                    l =
                        if (syncBatchModel1 == null)
                            db.insertWithOnConflict(
                                DatabaseHelper.sync_feedback_arr_mst,
                                null,
                                values,
                                SQLiteDatabase.CONFLICT_REPLACE
                            ) else

                            db.update(
                                DatabaseHelper.sync_feedback_arr_mst,
                                values,
                                "batch_id=? AND feedback_q_id = ?",
                                arrayOf(
                                    syncBatchModel1!!.batch_id.toString(),
                                    syncBatchModel1.feedback_q_id.toString()
                                )
                            ).toLong()
                } else {
                    val db = SyncUserAttendenceDataSource.getSQLiteDb(true, context)
                    l = db.insertWithOnConflict(
                        DatabaseHelper.sync_feedback_arr_mst,
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
            DatabaseHelper.created_at,
            DatabaseHelper.feedback_q_id,
            DatabaseHelper.response,
            DatabaseHelper.user_id,
            DatabaseHelper.user_type
        )


    private fun cursorData(cursor: Cursor): SyncFeedbackArray {
        val syncFeedbackArray = SyncFeedbackArray()
        syncFeedbackArray.batch_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        syncFeedbackArray.created_at =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.created_at))
        syncFeedbackArray.feedback_q_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.feedback_q_id))
        syncFeedbackArray.response =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.response))
        syncFeedbackArray.user_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))
        syncFeedbackArray.user_type =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_type))

        return syncFeedbackArray
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sync_feedback_arr_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSyncFeedbackData(context: Context?): ArrayList<SyncFeedbackArray> {
        synchronized(lock) {
            val feedbackResponse: ArrayList<SyncFeedbackArray> = ArrayList<SyncFeedbackArray>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.sync_feedback_arr_mst}",
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

    fun getByBatchIdFqId(batchId: String, fqId: String, context: Context?): SyncFeedbackArray? {
        synchronized(lock) {
            var syncFeedbackArray: SyncFeedbackArray? = null
            val db = getSQLiteDb(false, context)
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM " + DatabaseHelper.sync_feedback_arr_mst + " WHERE " + DatabaseHelper.batch_id + " = '" + batchId + "'" + " AND " + DatabaseHelper.feedback_q_id + " = '" + fqId + "'",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        syncFeedbackArray = cursorData(cursor)
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return syncFeedbackArray
        }
    }
}
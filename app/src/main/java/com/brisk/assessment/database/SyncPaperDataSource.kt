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
import com.brisk.assessment.model.SyncUserProfile

class SyncPaperDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SyncPaperDataSource? = null
        private val TAG = SyncPaperDataSource::class.java.simpleName
        val instance: SyncPaperDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SyncPaperDataSource()
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

    fun syncPaperData(syncPaperArray: SyncPaperArray, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.batch_id, syncPaperArray.batch_id)
                values.put(DatabaseHelper.consumed_time, syncPaperArray.consumed_time)
                values.put(DatabaseHelper.paper_set_id, syncPaperArray.paper_set_id)
                values.put(DatabaseHelper.paper_type_id, syncPaperArray.paper_type_id)
                values.put(DatabaseHelper.question_id, syncPaperArray.question_id)
                values.put(DatabaseHelper.squestion_id, syncPaperArray.squestion_id)
                values.put(DatabaseHelper.status, syncPaperArray.status)
                values.put(DatabaseHelper.sub_question_seq, syncPaperArray.sub_question_seq)
                values.put(DatabaseHelper.user_ans_id, syncPaperArray.user_ans_id)
                values.put(DatabaseHelper.user_id, syncPaperArray.user_id)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.sync_paper_arr_mst,
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
    private fun cursorData(cursor: Cursor): SyncPaperArray {
        val syncPaperArray = SyncPaperArray()
        syncPaperArray.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        syncPaperArray.consumed_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.consumed_time))
        syncPaperArray.paper_set_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.paper_set_id))
        syncPaperArray.paper_type_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.paper_type_id))
        syncPaperArray.question_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.question_id))
        syncPaperArray.squestion_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.squestion_id))
        syncPaperArray.status = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.status))
        syncPaperArray.sub_question_seq = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.sub_question_seq))
        syncPaperArray.user_ans_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_ans_id))
        syncPaperArray.user_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))

        return syncPaperArray
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sync_paper_arr_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSyncPaperData(context: Context?): ArrayList<SyncPaperArray> {
        synchronized(lock) {
            val feedbackResponse : ArrayList<SyncPaperArray> = ArrayList<SyncPaperArray>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.sync_paper_arr_mst}",
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
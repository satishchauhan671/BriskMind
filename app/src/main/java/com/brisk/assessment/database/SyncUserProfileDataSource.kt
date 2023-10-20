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
import com.brisk.assessment.model.SyncUserProfile

class SyncUserProfileDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: SyncUserProfileDataSource? = null
        private val TAG = SyncUserProfileDataSource::class.java.simpleName
        val instance: SyncUserProfileDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = SyncUserProfileDataSource()
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

    fun syncUserProfileData(syncUserProfileArray: SyncUserProfile, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.aadhar_no, syncUserProfileArray.aadhar_no)
                values.put(DatabaseHelper.batch_id, syncUserProfileArray.batch_id)
                values.put(DatabaseHelper.email_id, syncUserProfileArray.email_d)
                values.put(DatabaseHelper.mobile_no, syncUserProfileArray.mobile_no)
                values.put(DatabaseHelper.user_id, syncUserProfileArray.user_id)
                values.put(DatabaseHelper.user_name, syncUserProfileArray.user_name)
                values.put(DatabaseHelper.user_type, syncUserProfileArray.user_type)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.sync_user_profile_mst,
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
    private fun cursorData(cursor: Cursor): SyncUserProfile {
        val syncUserProfileArray = SyncUserProfile()
        syncUserProfileArray.aadhar_no = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.aadhar_no))
        syncUserProfileArray.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        syncUserProfileArray.email_d = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.email_id))
        syncUserProfileArray.mobile_no = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.mobile_no))
        syncUserProfileArray.user_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))
        syncUserProfileArray.user_name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_name))
        syncUserProfileArray.user_type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_type))

        return syncUserProfileArray
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.sync_user_profile_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getSyncUserProfileData(context: Context?): ArrayList<SyncUserProfile> {
        synchronized(lock) {
            val feedbackResponse : ArrayList<SyncUserProfile> = ArrayList<SyncUserProfile>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.sync_user_profile_mst}",
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
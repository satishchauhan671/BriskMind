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
import com.brisk.assessment.model.BatchConfigRes
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginRes

class BatchConfigDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: BatchConfigDataSource? = null
        private val TAG = BatchConfigDataSource::class.java.simpleName
        val instance: BatchConfigDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = BatchConfigDataSource()
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

    fun saveBatchConfigData(batchConfigRes: BatchConfigRes, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.batch_id, batchConfigRes.batch_id)
                values.put(DatabaseHelper.img_capture_time, batchConfigRes.img_capture_time)
                values.put(DatabaseHelper.option_randomize, batchConfigRes.option_randomize)
                values.put(DatabaseHelper.u_attendance_before_theory, batchConfigRes.u_attendance_before_theory)
                values.put(DatabaseHelper.u_attendance_after_theory, batchConfigRes.u_attendance_after_theory)
                values.put(DatabaseHelper.u_attendance_before_viva, batchConfigRes.u_attendance_before_viva)
                values.put(DatabaseHelper.u_attendance_after_viva, batchConfigRes.u_attendance_after_viva)
                values.put(DatabaseHelper.aadhar_front, batchConfigRes.aadhar_front)
                values.put(DatabaseHelper.aadhar_back, batchConfigRes.aadhar_back)
                values.put(DatabaseHelper.profile_pic, batchConfigRes.profile_pic)
                values.put(DatabaseHelper.assessor_attendance_before, batchConfigRes.assessor_attendance_before)
                values.put(DatabaseHelper.assessor_attendance_after, batchConfigRes.assessor_attendance_after)
                values.put(DatabaseHelper.mark_for_review, batchConfigRes.mark_for_review)
                values.put(DatabaseHelper.video_in_viva, batchConfigRes.video_in_viva)
                values.put(DatabaseHelper.candidate_profile, batchConfigRes.candidate_profile)
                values.put(DatabaseHelper.assessor_profile, batchConfigRes.assessor_profile)
                values.put(DatabaseHelper.candidate_feedback, batchConfigRes.candidate_feedback)
                values.put(DatabaseHelper.assessor_feedback, batchConfigRes.assessor_feedback)
                values.put(DatabaseHelper.attempt_all, batchConfigRes.attempt_all)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.batch_config_mst,
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

    private fun cursorData(cursor: Cursor): BatchConfigRes {
        val batchConfigRes = BatchConfigRes()

        batchConfigRes.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        batchConfigRes.img_capture_time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.img_capture_time))
        batchConfigRes.option_randomize = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.option_randomize))
        batchConfigRes.u_attendance_before_theory = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.u_attendance_before_theory))
        batchConfigRes.u_attendance_after_theory = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.u_attendance_after_theory))
        batchConfigRes.u_attendance_before_viva = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.u_attendance_before_viva))
        batchConfigRes.u_attendance_after_viva = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.u_attendance_after_viva))
        batchConfigRes.aadhar_front = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.aadhar_front))
        batchConfigRes.aadhar_back = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.aadhar_back))
        batchConfigRes.profile_pic = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.profile_pic))
        batchConfigRes.assessor_attendance_before = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.assessor_attendance_before))
        batchConfigRes.assessor_attendance_after = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.assessor_attendance_after))
        batchConfigRes.mark_for_review = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.mark_for_review))
        batchConfigRes.video_in_viva = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.video_in_viva))
        batchConfigRes.candidate_profile = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.candidate_profile))
        batchConfigRes.assessor_profile = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.assessor_profile))
        batchConfigRes.candidate_feedback = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.candidate_feedback))
        batchConfigRes.assessor_feedback = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.assessor_feedback))
        batchConfigRes.attempt_all = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.attempt_all))

        return batchConfigRes
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.batch_config_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getBatchConfigDataByBatchId(batchId: String,context: Context?): BatchConfigRes? {
        var batchConfigRes: BatchConfigRes? = null
        synchronized(lock) {
            val db: SQLiteDatabase = getSQLiteDb(false, context)
            try {
                val query = "SELECT * FROM " + DatabaseHelper.batch_config_mst + " WHERE " + DatabaseHelper.batch_id + " = '" + batchId + "'"
                val cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) batchConfigRes = cursorData(cursor)
                cursor.close()
                db.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
        }
        return batchConfigRes
    }
}
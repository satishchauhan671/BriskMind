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
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.PaperResponse

class BatchDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: BatchDataSource? = null
        private val TAG = BatchDataSource::class.java.simpleName
        val instance: BatchDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = BatchDataSource()
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

    fun saveBatchData(batchRes: BatchRes, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.assessor_name, batchRes.assessor_name)
                values.put(DatabaseHelper.batch_id, batchRes.batch_id)
                values.put(DatabaseHelper.sector_id, batchRes.sector_id)
                values.put(DatabaseHelper.sector_name, batchRes.sector_name)
                values.put(DatabaseHelper.batch_no, batchRes.batch_no)
                values.put(DatabaseHelper.assessment_date, batchRes.assessment_date)
                values.put(DatabaseHelper.duration, batchRes.duration)
                values.put(DatabaseHelper.client_id, batchRes.client_id)
                values.put(DatabaseHelper.alt_lang_id, batchRes.alt_lang_id)
                values.put(DatabaseHelper.s3_secret_key, batchRes.s3_secret_key)
                values.put(DatabaseHelper.s3_secret_pwd, batchRes.s3_secret_pwd)
                values.put(DatabaseHelper.s3_group_path, batchRes.s3_group_path)
                values.put(DatabaseHelper.total_practical_marks, batchRes.total_practical_marks)
                values.put(DatabaseHelper.total_theory_marks, batchRes.total_theory_marks)
                values.put(DatabaseHelper.total_viva_marks, batchRes.total_viva_marks)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.batch_mst,
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


    //  add increased column here also
    private val queryData: Array<String>
        private get() = arrayOf(
            DatabaseHelper.assessor_name,
            DatabaseHelper.batch_id,
            DatabaseHelper.sector_id,
            DatabaseHelper.sector_name,
            DatabaseHelper.batch_no,
            DatabaseHelper.assessment_date,
            DatabaseHelper.duration,
            DatabaseHelper.client_id,
            DatabaseHelper.alt_lang_id,
            DatabaseHelper.s3_group_path,
            DatabaseHelper.s3_secret_key,
            DatabaseHelper.s3_secret_pwd,
            DatabaseHelper.total_practical_marks,
            DatabaseHelper.total_viva_marks,
            DatabaseHelper.total_theory_marks
        )

    private fun cursorData(cursor: Cursor): BatchRes {
        val batchRes = BatchRes()
        batchRes.assessor_name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.assessor_name))
        batchRes.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        batchRes.sector_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.sector_id))
        batchRes.sector_name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.sector_name))
        batchRes.batch_no = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_no))
        batchRes.assessment_date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.assessment_date))
        batchRes.duration = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.duration))
        batchRes.client_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.client_id))
        batchRes.alt_lang_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.alt_lang_id))
        batchRes.s3_secret_key = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.s3_secret_key))
        batchRes.s3_secret_pwd = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.s3_secret_pwd))
        batchRes.s3_group_path = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.s3_group_path))
        batchRes.total_practical_marks = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.total_practical_marks))
        batchRes.total_viva_marks = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.total_viva_marks))
        batchRes.total_theory_marks = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.total_theory_marks))

        return batchRes
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.batch_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getBatchData(context: Context?): ArrayList<BatchRes>? {
        synchronized(lock) {
            val batchRes : ArrayList<BatchRes> = ArrayList<BatchRes>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.batch_mst}",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        batchRes.add(cursorData(cursor))
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return batchRes
        }

    }


    fun getBatchByBatchId(batchId : String , context: Context?): BatchRes? {
        var batchRes: BatchRes? = null
        synchronized(lock) {
            val db: SQLiteDatabase = getSQLiteDb(false, context)
            try {

                val query = "SELECT * FROM " + DatabaseHelper.batch_mst + " WHERE " + DatabaseHelper.batch_id + " = '" + batchId + "'"
                val cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) batchRes = cursorData(cursor)
                cursor.close()
                db.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
        }
        return batchRes
    }
}
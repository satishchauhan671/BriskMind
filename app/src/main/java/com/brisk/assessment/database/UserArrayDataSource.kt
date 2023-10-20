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
import com.brisk.assessment.model.UserResponse

class UserArrayDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: UserArrayDataSource? = null
        private val TAG = UserArrayDataSource::class.java.simpleName
        val instance: UserArrayDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = UserArrayDataSource()
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

    fun saveUserArrayData(userResponse: UserResponse, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.batch_id, userResponse.batch_id)
                values.put(DatabaseHelper.user_type, userResponse.user_type)
                values.put(DatabaseHelper.login_id, userResponse.login_id)
                values.put(DatabaseHelper.login_pass, userResponse.login_pass)
                values.put(DatabaseHelper.user_id, userResponse.user_id)
                values.put(DatabaseHelper.user_name, userResponse.user_name)
                values.put(DatabaseHelper.enrollment_no, userResponse.enrollment_no)
                values.put(DatabaseHelper.candidate_id, userResponse.candidate_id)
                values.put(DatabaseHelper.father_name, userResponse.father_name)
                values.put(DatabaseHelper.mobile_no, userResponse.mobile_no)
                values.put(DatabaseHelper.jobrole, userResponse.jobrole)
                values.put(DatabaseHelper.theory_paper_set_id, userResponse.theory_paper_set_id)
                values.put(DatabaseHelper.viva_paper_set_id, userResponse.viva_paper_set_id)
                values.put(
                    DatabaseHelper.practical_paper_set_id,
                    userResponse.practical_paper_set_id
                )
                values.put(DatabaseHelper.alt_lang_id, userResponse.alt_lang_id)
                values.put(DatabaseHelper.client_id, userResponse.client_id)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.user_data_mst,
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


    private fun cursorData(cursor: Cursor): UserResponse {
        val userResponse = UserResponse()
        userResponse.batch_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        userResponse.user_type =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_type))
        userResponse.login_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.login_id))
        userResponse.login_pass =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.login_pass))
        userResponse.user_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))
        userResponse.user_name =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_name))
        userResponse.enrollment_no =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.enrollment_no))
        userResponse.candidate_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.candidate_id))
        userResponse.father_name =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.father_name))
        userResponse.mobile_no =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.mobile_no))
        userResponse.jobrole =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.jobrole))
        userResponse.theory_paper_set_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.theory_paper_set_id))
        userResponse.viva_paper_set_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.viva_paper_set_id))
        userResponse.practical_paper_set_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.practical_paper_set_id))
        userResponse.alt_lang_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.alt_lang_id))
        userResponse.client_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.client_id))

        return userResponse
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.user_data_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getUserArrayData(context: Context?): UserResponse? {
        var userResponse: UserResponse? = null
        synchronized(lock) {
            val db: SQLiteDatabase = getSQLiteDb(false, context)
            try {
                val query = "SELECT * FROM ${DatabaseHelper.user_data_mst}"
                val cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) userResponse = cursorData(cursor)
                cursor.close()
                db.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
        }
        return userResponse
    }

    fun getUserArrayDataByBatchId(batchId :  String ,context: Context?): ArrayList<UserResponse>? {
        synchronized(lock) {
            val userResponse : ArrayList<UserResponse> = ArrayList<UserResponse>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM " + DatabaseHelper.user_data_mst + " WHERE " + DatabaseHelper.batch_id + " = '" + batchId + "'",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        userResponse.add(cursorData(cursor))
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return userResponse
        }

    }

}
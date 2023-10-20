package com.brisk.assessment.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.brisk.assessment.database.DatabaseHelper.Companion.getInstance
import com.brisk.assessment.database.DatabaseHelper.Companion.lock
import com.brisk.assessment.database.DatabaseHelper.Companion.login_mst
import com.brisk.assessment.model.LoginRes

class LoginDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: LoginDataSource? = null
        private val TAG = LoginDataSource::class.java.simpleName
        val instance: LoginDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = LoginDataSource()
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

    fun saveUserDatabaseData(loginResponseModel: LoginRes, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.id, loginResponseModel.id)
                values.put(DatabaseHelper.batch_id, loginResponseModel.batch_id)
                values.put(DatabaseHelper.client_id, loginResponseModel.client_id)
                values.put(DatabaseHelper.login_app_type, loginResponseModel.login_app_type)
                values.put(DatabaseHelper.login_type, loginResponseModel.login_type)
                values.put(DatabaseHelper.message, loginResponseModel.message)
                values.put(DatabaseHelper.server_ip, loginResponseModel.server_ip)
                values.put(DatabaseHelper.status, loginResponseModel.status)
                values.put(DatabaseHelper.user_id, loginResponseModel.user_id)
                values.put(DatabaseHelper.user_name, loginResponseModel.user_name)

                var l: Long = 0
                // check account number if not null
                if (loginResponseModel.user_id != null) {
                    val loginResponseModel = getByUserCode(loginResponseModel.user_id!!, context)
                    val db = getSQLiteDb(true, context)

                    l = if (loginResponseModel == null) db.insertWithOnConflict(
                        DatabaseHelper.login_mst,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE
                    ) else db.update(
                        DatabaseHelper.login_mst,
                        values,
                        DatabaseHelper.user_id + "=?",
                        arrayOf(loginResponseModel.user_id)
                    ).toLong()

                    //db.close;
                } else {
                    val db = getSQLiteDb(true, context)

                    l = db.insertWithOnConflict(
                        DatabaseHelper.user_id,
                        null,
                        values,
                        SQLiteDatabase.CONFLICT_REPLACE
                    )
                    //db.close;

                }
                if (l > -1) a = true
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return a
        }
    }


    //  add increased column here also
    private val queryData: Array<String>
        private get() = arrayOf(
            DatabaseHelper.id,
            DatabaseHelper.batch_id,
            DatabaseHelper.client_id,
            DatabaseHelper.login_app_type,
            DatabaseHelper.login_type,
            DatabaseHelper.message,
            DatabaseHelper.server_ip,
            DatabaseHelper.status,
            DatabaseHelper.user_id,
            DatabaseHelper.user_name
        )

    private fun cursorData(cursor: Cursor): LoginRes {
        val loginResponseModel = LoginRes()
        loginResponseModel.id =
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.id))
        loginResponseModel.batch_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.batch_id))
        loginResponseModel.client_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.client_id))
        loginResponseModel.login_app_type =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.login_app_type))
        loginResponseModel.login_type =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.login_type))
        loginResponseModel.message =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.message))
        loginResponseModel.server_ip =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.server_ip))
        loginResponseModel.status =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.status))
        loginResponseModel.user_id =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_id))
        loginResponseModel.user_name =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.user_name))

        return loginResponseModel
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + login_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getLogin(context: Context?): LoginRes? {
        var login: LoginRes? = null
        synchronized(lock) {
            val db: SQLiteDatabase = getSQLiteDb(false, context)
            try {
                val query = "SELECT * FROM $login_mst"
                val cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) login = cursorData(cursor)
                cursor.close()
                db.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
        }
        return login
    }


    fun getByUserCode(userCode: String, context: Context?): LoginRes? {
        synchronized(lock) {
            var loginResponseModel: LoginRes? = null
            val db = getSQLiteDb(false, context)
            try {
                val cursor = db.query(
                    login_mst,
                    queryData,
                    DatabaseHelper.user_id + "=?",
                    arrayOf(userCode),
                    null,
                    null,
                    null
                )
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    loginResponseModel = cursorData(cursor)
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return loginResponseModel
        }
    }

}
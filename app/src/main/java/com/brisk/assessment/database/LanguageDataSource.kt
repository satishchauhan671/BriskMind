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
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginRes

class LanguageDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: LanguageDataSource? = null
        private val TAG = LanguageDataSource::class.java.simpleName
        val instance: LanguageDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = LanguageDataSource()
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

    fun saveLanguageData(languageRes: LanguageRes, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.label, languageRes.label)
                values.put(DatabaseHelper.language_id, languageRes.language_id)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.languages_mst,
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
            DatabaseHelper.id,
            DatabaseHelper.label,
            DatabaseHelper.language_id,
        )

    private fun cursorData(cursor: Cursor): LanguageRes {
        val languageRes = LanguageRes()
        languageRes.language_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.language_id))
        languageRes.label =
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.label))

        return languageRes
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + languages_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getLanguage(context: Context?): LanguageRes? {
        var languageRes: LanguageRes? = null
        synchronized(lock) {
            val db: SQLiteDatabase = getSQLiteDb(false, context)
            try {
                val query = "SELECT * FROM $languages_mst"
                val cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) languageRes = cursorData(cursor)
                cursor.close()
                db.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
        }
        return languageRes
    }
}
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
import com.brisk.assessment.model.TextsRes

class LanguageTextDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: LanguageTextDataSource? = null
        private val TAG = LanguageTextDataSource::class.java.simpleName
        val instance: LanguageTextDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = LanguageTextDataSource()
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

    fun saveLanguageTextData(textsRes: TextsRes, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.clear, textsRes.clear)
                values.put(DatabaseHelper.instruction, textsRes.instruction)
                values.put(DatabaseHelper.login, textsRes.login)
                values.put(DatabaseHelper.logout, textsRes.logout)
                values.put(DatabaseHelper.next, textsRes.next)
                values.put(DatabaseHelper.not_now, textsRes.not_now)
                values.put(DatabaseHelper.start_theory, textsRes.start_theory)
                values.put(DatabaseHelper.submit, textsRes.submit)
                values.put(DatabaseHelper.text_instruction, textsRes.text_instruction)
                values.put(DatabaseHelper.language_id, textsRes.language_id)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.languages_text_mst,
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
            DatabaseHelper.clear,
            DatabaseHelper.instruction,
            DatabaseHelper.login,
            DatabaseHelper.logout,
            DatabaseHelper.next,
            DatabaseHelper.not_now,
            DatabaseHelper.start_theory,
            DatabaseHelper.submit,
            DatabaseHelper.text_instruction,
            DatabaseHelper.language_id
        )

    private fun cursorData(cursor: Cursor): TextsRes {
        val textsRes = TextsRes()
        textsRes.clear = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.clear))
        textsRes.instruction = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.instruction))
        textsRes.login = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.login))
        textsRes.logout = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.logout))
        textsRes.next = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.next))
        textsRes.not_now = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.not_now))
        textsRes.start_theory = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.start_theory))
        textsRes.submit = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.submit))
        textsRes.text_instruction = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.text_instruction))
        textsRes.language_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.language_id))

        return textsRes
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.languages_text_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getLanguageText(context: Context?): TextsRes? {
        var textsRes: TextsRes? = null
        synchronized(lock) {
            val db: SQLiteDatabase = getSQLiteDb(false, context)
            try {
                val query = "SELECT * FROM $DatabaseHelper.languages_text_mst"
                val cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) textsRes = cursorData(cursor)
                cursor.close()
                db.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
        }
        return textsRes
    }
}
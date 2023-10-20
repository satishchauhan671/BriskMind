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
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginRes

class ImportLanuageDataSource private constructor() {

    companion object {
        private var hasObject = false
        private var myDataSource: ImportLanuageDataSource? = null
        private val TAG = ImportLanuageDataSource::class.java.simpleName
        val instance: ImportLanuageDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = ImportLanuageDataSource()
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

    fun saveImportLanguageData(importLanguageResponse: ImportLanguageResponse, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.language_id, importLanguageResponse.language_id)
                values.put(DatabaseHelper.language_name, importLanguageResponse.language_name)
                values.put(DatabaseHelper.theory_instructions, importLanguageResponse.theory_instructions)
                values.put(DatabaseHelper.viva_instructions, importLanguageResponse.viva_instructions)
                values.put(DatabaseHelper.practical_instructions, importLanguageResponse.practical_instructions)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.import_language_mst,
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
    private fun cursorData(cursor: Cursor): ImportLanguageResponse {
        val importLanguageResponse = ImportLanguageResponse()
        importLanguageResponse.language_id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.language_id))
        importLanguageResponse.language_name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.language_name))
        importLanguageResponse.practical_instructions = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.practical_instructions))
        importLanguageResponse.viva_instructions = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.viva_instructions))
        importLanguageResponse.theory_instructions = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.theory_instructions))

        return importLanguageResponse
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.import_language_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getImportLanguageData(context: Context?): ArrayList<ImportLanguageResponse> {
        synchronized(lock) {
            val importLanguageResponse : ArrayList<ImportLanguageResponse> = ArrayList<ImportLanguageResponse>()
            val db: SQLiteDatabase = getSQLiteDb(
                false,
                context
            )
            try {
                val cursor = db.rawQuery(
                    "SELECT * FROM ${DatabaseHelper.import_language_mst}",
                    null
                )
                if (cursor.moveToFirst()) {
                    do {
                        importLanguageResponse.add(cursorData(cursor))
                    } while (cursor.moveToNext())
                }
                cursor.close()
                //db.close;
            } catch (se: SQLException) {
                se.printStackTrace()
            }
            return importLanguageResponse
        }
    }
}
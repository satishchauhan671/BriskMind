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
import com.brisk.assessment.model.OptionRes
import com.brisk.assessment.model.TransOptionRes

class TransOptionDataSource private constructor() {
    companion object {
        private var hasObject = false
        private var myDataSource: TransOptionDataSource? = null
        private val TAG = TransOptionDataSource::class.java.simpleName
        val instance: TransOptionDataSource?
            get() = if (hasObject) {
                myDataSource
            } else {
                hasObject = true
                myDataSource = TransOptionDataSource()
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

    fun saveOptionArrayData(optionRes: TransOptionRes, context: Context?): Boolean {
        var a = false
        synchronized(lock) {
            try {
                val values = ContentValues()
                values.put(DatabaseHelper.id, optionRes.id)
                values.put(DatabaseHelper.option, optionRes.option)
                values.put(DatabaseHelper.option_image, optionRes.option_image)
                values.put(DatabaseHelper.subQuestionId, optionRes.subQuestionId)

                val db = getSQLiteDb(true, context)
                val l: Long = db.insertWithOnConflict(
                    DatabaseHelper.trans_option_mst,
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

    private fun cursorData(cursor: Cursor): TransOptionRes {
        val optionRes = TransOptionRes()
        optionRes.id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.id))
        optionRes.option = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.option))
        optionRes.option_image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.option_image))
        optionRes.subQuestionId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.subQuestionId))

        return optionRes
    }

    fun deleteAll(context: Context?) {
        synchronized(lock) {
            try {
                val db = getSQLiteDb(true, context)
                db.execSQL("DELETE FROM " + DatabaseHelper.trans_option_mst)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    fun getOptionArrayData(context: Context?): TransOptionRes? {
        var optionRes: TransOptionRes? = null
        synchronized(lock) {
            val db: SQLiteDatabase = getSQLiteDb(false, context)
            try {
                val query = "SELECT * FROM ${DatabaseHelper.trans_option_mst}"
                val cursor = db.rawQuery(query, null)
                if (cursor.moveToFirst()) optionRes = cursorData(cursor)
                cursor.close()
                db.close()
            } catch (se: SQLException) {
                se.printStackTrace()
            }
        }
        return optionRes
    }
}
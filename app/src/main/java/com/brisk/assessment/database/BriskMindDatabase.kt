package com.brisk.assessment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brisk.assessment.common.DataConvertor
import com.brisk.assessment.dao.LanguageDao
import com.brisk.assessment.dao.TextsDao
import com.brisk.assessment.dao.UserDao
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.TextsRes

@TypeConverters(DataConvertor::class)
@Database(entities = [LoginRes::class,LanguageRes::class,TextsRes::class], version = 1, exportSchema = false)
abstract class BriskMindDatabase : RoomDatabase(){

    abstract fun userDao() : UserDao
    abstract fun languageDao() : LanguageDao
    abstract fun textsDao() : TextsDao

    companion object {
        @Volatile
        private var INSTANCE : BriskMindDatabase? = null

        fun getDatabaseInstance(context: Context) : BriskMindDatabase{
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        BriskMindDatabase::class.java,
                        "briskMind.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}
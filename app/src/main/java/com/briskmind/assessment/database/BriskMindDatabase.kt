package com.briskmind.assessment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.briskmind.assessment.dao.AssessorDao
import com.briskmind.assessment.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class BriskMindDatabase : RoomDatabase(){
    abstract fun assessorDao() : AssessorDao

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
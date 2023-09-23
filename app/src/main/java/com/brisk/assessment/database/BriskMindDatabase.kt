package com.brisk.assessment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brisk.assessment.common.DataConvertor
import com.brisk.assessment.dao.BatchConfigDao
import com.brisk.assessment.dao.BatchDao
import com.brisk.assessment.dao.FeedbackDao
import com.brisk.assessment.dao.ImportLanguageDao
import com.brisk.assessment.dao.LanguageDao
import com.brisk.assessment.dao.TextsDao
import com.brisk.assessment.dao.LoginDao
import com.brisk.assessment.dao.OptionDao
import com.brisk.assessment.dao.PaperDao
import com.brisk.assessment.dao.SubQuestionDao
import com.brisk.assessment.dao.TransOptionDao
import com.brisk.assessment.dao.UserDao
import com.brisk.assessment.model.BatchConfigRes
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LanguageRes
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.OptionRes
import com.brisk.assessment.model.PaperResponse
import com.brisk.assessment.model.SubQuestionResponse
import com.brisk.assessment.model.TextsRes
import com.brisk.assessment.model.TransOptionRes
import com.brisk.assessment.model.UserResponse

@TypeConverters(DataConvertor::class)
@Database(
    entities = [LoginRes::class, LanguageRes::class, TextsRes::class, BatchRes::class, BatchConfigRes::class,
        UserResponse::class, ImportLanguageResponse::class, FeedbackResponse::class,PaperResponse::class,
               SubQuestionResponse::class, OptionRes::class, TransOptionRes::class],
    version = 1,
    exportSchema = false
)
abstract class BriskMindDatabase : RoomDatabase() {

    abstract fun loginDao(): LoginDao
    abstract fun languageDao(): LanguageDao
    abstract fun textsDao(): TextsDao
    abstract fun batchDao(): BatchDao
    abstract fun batchConfigDao(): BatchConfigDao
    abstract fun paperDao(): PaperDao
    abstract fun subQuestionsDao(): SubQuestionDao
    abstract fun userDao(): UserDao
    abstract fun importLanguageDao(): ImportLanguageDao
    abstract fun feedbackDao(): FeedbackDao
    abstract fun optionDao() : OptionDao
    abstract fun transOptionDao() : TransOptionDao

    companion object {
        @Volatile
        private var INSTANCE: BriskMindDatabase? = null

        fun getDatabaseInstance(context: Context): BriskMindDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
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
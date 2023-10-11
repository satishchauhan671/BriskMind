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
import com.brisk.assessment.dao.SyncAssessorAttendanceDao
import com.brisk.assessment.dao.SyncBatchDao
import com.brisk.assessment.dao.SyncUserAttendanceDao
import com.brisk.assessment.dao.SyncFeedbackDao
import com.brisk.assessment.dao.SyncImageDao
import com.brisk.assessment.dao.SyncLogDao
import com.brisk.assessment.dao.SyncPaperDao
import com.brisk.assessment.dao.SyncUserDao
import com.brisk.assessment.dao.SyncUserPracticalAttendanceDao
import com.brisk.assessment.dao.SyncUserProfileDao
import com.brisk.assessment.dao.SyncUserTheoryAttendanceDao
import com.brisk.assessment.dao.SyncUserVivaAttendanceDao
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
import com.brisk.assessment.model.SyncAssessorAttendance
import com.brisk.assessment.model.SyncBatchArray
import com.brisk.assessment.model.SyncFeedbackArray
import com.brisk.assessment.model.SyncImageArray
import com.brisk.assessment.model.SyncLogArray
import com.brisk.assessment.model.SyncPaperArray
import com.brisk.assessment.model.SyncUserArray
import com.brisk.assessment.model.SyncUserAttendance
import com.brisk.assessment.model.SyncUserPracticalAttendance
import com.brisk.assessment.model.SyncUserProfile
import com.brisk.assessment.model.SyncUserTheoryAttendance
import com.brisk.assessment.model.SyncUserVivaAttendance
import com.brisk.assessment.model.TextsRes
import com.brisk.assessment.model.TransOptionRes
import com.brisk.assessment.model.UserResponse

@TypeConverters(DataConvertor::class)
@Database(
    entities = [LoginRes::class, LanguageRes::class, TextsRes::class, BatchRes::class, BatchConfigRes::class,
        UserResponse::class, ImportLanguageResponse::class, FeedbackResponse::class,PaperResponse::class,
               SubQuestionResponse::class, OptionRes::class, TransOptionRes::class, SyncBatchArray::class,
               SyncUserAttendance::class, SyncUserTheoryAttendance::class, SyncUserVivaAttendance::class,
               SyncUserPracticalAttendance::class, SyncAssessorAttendance::class, SyncUserProfile::class,
               SyncFeedbackArray::class,SyncPaperArray::class,SyncUserArray::class, SyncImageArray::class,
               SyncLogArray::class],
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

    // sync dao
    abstract fun syncBatchDao(): SyncBatchDao
    abstract fun syncUserAttendanceDao(): SyncUserAttendanceDao
    abstract fun syncUserTheoryAttendanceDao(): SyncUserTheoryAttendanceDao
    abstract fun syncUserVivaAttendanceDao(): SyncUserVivaAttendanceDao
    abstract fun syncUserPracticalAttendanceDao(): SyncUserPracticalAttendanceDao
    abstract fun syncAssessorAttendanceDao(): SyncAssessorAttendanceDao
    abstract fun syncUserProfileDao(): SyncUserProfileDao
    abstract fun syncUserFeedbackDao(): SyncFeedbackDao
    abstract fun syncPaperDao(): SyncPaperDao
    abstract fun syncUserDao(): SyncUserDao
    abstract fun syncImageDao(): SyncImageDao
    abstract fun syncLogDao(): SyncLogDao


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
package com.brisk.assessment.repositories

import android.app.Application
import com.brisk.assessment.database.BriskMindDatabase
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
import com.brisk.assessment.retrofit.ApiClient

class AssessorRepo(private val application: Application) {

    private val networkService = ApiClient.getApiClient()
    private val briskMindDatabase = BriskMindDatabase.getDatabaseInstance(application)
    
    suspend fun saveBatchDetail(syncBatchArray: SyncBatchArray){
        return briskMindDatabase.syncBatchDao().insert(syncBatchArray)
    }

    // user attendance
    suspend fun saveUserAttendance(syncUserAttendance: SyncUserAttendance){
        return briskMindDatabase.syncUserAttendanceDao().insert(syncUserAttendance)
    }

    suspend fun saveAllUserAttendance(syncUserAttendance: List<SyncUserAttendance>){
        return briskMindDatabase.syncUserAttendanceDao().insertAll(syncUserAttendance)
    }

    // theory attendance
    suspend fun saveUserTheoryAttendance(syncUserTheoryAttendance: SyncUserTheoryAttendance){
        return briskMindDatabase.syncUserTheoryAttendanceDao().insert(syncUserTheoryAttendance)
    }

    suspend fun saveAllUserTheoryAttendance(syncUserTheoryAttendanceList: List<SyncUserTheoryAttendance>){
        return briskMindDatabase.syncUserTheoryAttendanceDao().insertAll(syncUserTheoryAttendanceList)
    }

    // viva attendance
    suspend fun saveUserVivaAttendance(syncUserVivaAttendance: SyncUserVivaAttendance){
        return briskMindDatabase.syncUserVivaAttendanceDao().insert(syncUserVivaAttendance)
    }

    suspend fun saveAllUserVivaTheoryAttendance(syncUserVivaAttendanceList: List<SyncUserVivaAttendance>){
        return briskMindDatabase.syncUserVivaAttendanceDao().insertAll(syncUserVivaAttendanceList)
    }

    // practical attendance
    suspend fun saveUserPracticalAttendance(syncUserPracticalAttendance: SyncUserPracticalAttendance){
        return briskMindDatabase.syncUserPracticalAttendanceDao().insert(syncUserPracticalAttendance)
    }

    suspend fun saveAllUserPracticalAttendance(syncUserTheoryAttendanceList: List<SyncUserPracticalAttendance>){
        return briskMindDatabase.syncUserPracticalAttendanceDao().insertAll(syncUserTheoryAttendanceList)
    }

    // assessor attendance
    suspend fun saveAssessorAttendance(syncAssessorAttendance: SyncAssessorAttendance){
        return briskMindDatabase.syncAssessorAttendanceDao().insert(syncAssessorAttendance)
    }

    suspend fun saveAllAssessorAttendance(syncAssessorAttendance: List<SyncAssessorAttendance>){
        return briskMindDatabase.syncAssessorAttendanceDao().insertAll(syncAssessorAttendance)
    }

    // user profile
    suspend fun saveUserProfile(syncUserProfile: SyncUserProfile){
        return briskMindDatabase.syncUserProfileDao().insert(syncUserProfile)
    }

    suspend fun saveUserProfiles(syncUserProfile: List<SyncUserProfile>){
        return briskMindDatabase.syncUserProfileDao().insertAll(syncUserProfile)
    }

    // feedback
    suspend fun saveFeedback(syncFeedbackArray: SyncFeedbackArray){
        return briskMindDatabase.syncUserFeedbackDao().insert(syncFeedbackArray)
    }

    suspend fun saveAllFeedback(syncFeedbackArray: List<SyncFeedbackArray>){
        return briskMindDatabase.syncUserFeedbackDao().insertAll(syncFeedbackArray)
    }

    // paper
    suspend fun savePaper(syncPaper: SyncPaperArray){
        return briskMindDatabase.syncPaperDao().insert(syncPaper)
    }

    suspend fun saveAllPaper(syncPaperArray: List<SyncPaperArray>){
        return briskMindDatabase.syncPaperDao().insertAll(syncPaperArray)
    }

    // user
    suspend fun saveUser(syncUser: SyncUserArray){
        return briskMindDatabase.syncUserDao().insert(syncUser)
    }

    suspend fun saveAllUser(syncUserArray: List<SyncUserArray>){
        return briskMindDatabase.syncUserDao().insertAll(syncUserArray)
    }

    // image
    suspend fun saveImage(syncImage: SyncImageArray){
        return briskMindDatabase.syncImageDao().insert(syncImage)
    }

    suspend fun saveAllImage(syncImageArray: List<SyncImageArray>){
        return briskMindDatabase.syncImageDao().insertAll(syncImageArray)
    }

    // logs
    suspend fun saveLog(syncLog: SyncLogArray){
        return briskMindDatabase.syncLogDao().insert(syncLog)
    }

    suspend fun saveLogs(syncLogArray: List<SyncLogArray>){
        return briskMindDatabase.syncLogDao().insertAll(syncLogArray)
    }
}
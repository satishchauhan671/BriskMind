package com.brisk.assessment.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brisk.assessment.common.NetworkResult
import com.brisk.assessment.model.LoginRes
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
import com.brisk.assessment.repositories.AssessorRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AssessorViewModel(private val application: Application) : ViewModel() {
    private val updatedSyncBatchLiveData = MutableLiveData<SyncBatchArray>()
    val assessorRepo = AssessorRepo(application)
    // Expose it as LiveData to the fragment
    val updatedSyncBatch: LiveData<SyncBatchArray>
        get() = updatedSyncBatchLiveData
     fun saveBatchDetail(syncBatchArray: SyncBatchArray){
        viewModelScope.launch(Dispatchers.IO) {
            assessorRepo.saveBatchDetail(syncBatchArray)
        }
    }

     fun updateBatchDetail(syncBatchArray: SyncBatchArray){
        viewModelScope.launch(Dispatchers.IO) {
            assessorRepo.updateBatchDetail(syncBatchArray)
        }
    }


    suspend fun isBatchIdExists(batchId : String) : Boolean{
        return assessorRepo.isBatchIdExists(batchId)
    }

    // user attendance
    suspend fun saveUserAttendance(syncUserAttendance: SyncUserAttendance){
        return assessorRepo.saveUserAttendance(syncUserAttendance)
    }

    suspend fun saveAllUserAttendance(syncUserAttendance: List<SyncUserAttendance>){
        return assessorRepo.saveAllUserAttendance(syncUserAttendance)
    }

    // theory attendance
    suspend fun saveUserTheoryAttendance(syncUserTheoryAttendance: SyncUserTheoryAttendance){
        return assessorRepo.saveUserTheoryAttendance(syncUserTheoryAttendance)
    }

    suspend fun saveAllUserTheoryAttendance(syncUserTheoryAttendanceList: List<SyncUserTheoryAttendance>){
        return assessorRepo.saveAllUserTheoryAttendance(syncUserTheoryAttendanceList)
    }

    // viva attendance
    suspend fun saveUserVivaAttendance(syncUserVivaAttendance: SyncUserVivaAttendance){
        return assessorRepo.saveUserVivaAttendance(syncUserVivaAttendance)
    }

    suspend fun saveAllUserVivaTheoryAttendance(syncUserVivaAttendanceList: List<SyncUserVivaAttendance>){
        return assessorRepo.saveAllUserVivaTheoryAttendance(syncUserVivaAttendanceList)
    }

    // practical attendance
    suspend fun saveUserPracticalAttendance(syncUserPracticalAttendance: SyncUserPracticalAttendance){
        return assessorRepo.saveUserPracticalAttendance(syncUserPracticalAttendance)
    }

    suspend fun saveAllUserPracticalAttendance(syncUserTheoryAttendanceList: List<SyncUserPracticalAttendance>){
        return assessorRepo.saveAllUserPracticalAttendance(syncUserTheoryAttendanceList)
    }

    // assessor attendance
    suspend fun saveAssessorAttendance(syncAssessorAttendance: SyncAssessorAttendance){
        return assessorRepo.saveAssessorAttendance(syncAssessorAttendance)
    }

    suspend fun saveAllAssessorAttendance(syncAssessorAttendance: List<SyncAssessorAttendance>){
        return assessorRepo.saveAllAssessorAttendance(syncAssessorAttendance)
    }

    // user profile
    suspend fun saveUserProfile(syncUserProfile: SyncUserProfile){
        return assessorRepo.saveUserProfile(syncUserProfile)
    }

    suspend fun saveUserProfiles(syncUserProfile: List<SyncUserProfile>){
        return assessorRepo.saveUserProfiles(syncUserProfile)
    }

    // feedback
    suspend fun saveFeedback(syncFeedbackArray: SyncFeedbackArray){
        return assessorRepo.saveFeedback(syncFeedbackArray)
    }

    suspend fun saveAllFeedback(syncFeedbackArray: List<SyncFeedbackArray>){
        return assessorRepo.saveAllFeedback(syncFeedbackArray)
    }

    // paper
    suspend fun savePaper(syncPaper: SyncPaperArray){
        return assessorRepo.savePaper(syncPaper)
    }

    suspend fun saveAllPaper(syncPaperArray: List<SyncPaperArray>){
        return assessorRepo.saveAllPaper(syncPaperArray)
    }

    // user
    suspend fun saveUser(syncUser: SyncUserArray){
        return assessorRepo.saveUser(syncUser)
    }

    suspend fun saveAllUser(syncUserArray: List<SyncUserArray>){
        return assessorRepo.saveAllUser(syncUserArray)
    }

    // image
    suspend fun saveImage(syncImage: SyncImageArray){
        return assessorRepo.saveImage(syncImage)
    }

    suspend fun saveAllImage(syncImageArray: List<SyncImageArray>){
        return assessorRepo.saveAllImage(syncImageArray)
    }

    // logs
    suspend fun saveLog(syncLog: SyncLogArray){
        return assessorRepo.saveLog(syncLog)
    }

    suspend fun saveLogs(syncLogArray: List<SyncLogArray>){
        return assessorRepo.saveLogs(syncLogArray)
    }
}
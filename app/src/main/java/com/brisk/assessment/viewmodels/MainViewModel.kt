package com.brisk.assessment.viewmodels

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brisk.assessment.common.NetworkResult
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.ImportAssessmentReq
import com.brisk.assessment.model.ImportAssessmentResponse
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.OptionRes
import com.brisk.assessment.model.PaperResponse
import com.brisk.assessment.model.SubQuestionResponse
import com.brisk.assessment.model.TransOptionRes
import com.brisk.assessment.model.UserResponse
import com.brisk.assessment.repositories.LoginRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val loginRepo: LoginRepo) : ViewModel() {

    val loginRes: LiveData<NetworkResult<LoginRes>>
        get() = loginRepo.loginRes
    val importAssessmentRes: LiveData<NetworkResult<ImportAssessmentResponse>>
        get() = loginRepo.importAssessmentRes

    fun login(loginReq: LoginReq) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepo.login(
                loginReq
            )
        }
    }

    fun importAssessmentData(importAssessment: ImportAssessmentReq) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepo.importAssessment(
                importAssessment
            )
        }
    }

    fun isLoginValidRequest(loginId: String?, password: String?): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(loginId)) {
            result = Pair(false, "Please Enter Login Id")
        } else if (TextUtils.isEmpty(password)) {
            result = Pair(false, "Please Enter Password")
        }
        return result
    }

    fun getLoginData() : LiveData<LoginRes>{
        return loginRepo.getLoginData()
    }

    // Get Data from Batch Mst
    fun getAssessorBatchList() : LiveData<List<BatchRes>> {
        return loginRepo.getAssessorBatchList()
    }

    fun getBatchByBatchId(batchId : String) : LiveData<BatchRes>{
        return loginRepo.getBatchByBatchId(batchId)
    }



    fun getPaperListByPaperSetId(paperSetId : String) : LiveData<List<PaperResponse>>{
        return loginRepo.getPaperListByPaperSetId(paperSetId)
    }

    fun getUserByBatchId(batchId : String) : LiveData<List<UserResponse>>{
        return  loginRepo.getUserByBatchId(batchId)
    }

    // Sub-Questions
    fun getSubQueByQueId(queId : String) : LiveData<List<SubQuestionResponse>>{
        return  loginRepo.getSubQueByQueId(queId)
    }

    // Options
    fun getOptionBySubQuestionId(batchId : String) : LiveData<List<OptionRes>>{
        return  loginRepo.getOptionBySubQuestionId(batchId)
    }

    fun getTransOptionBySubQuestionId(batchId : String) : LiveData<List<TransOptionRes>>{
        return  loginRepo.getTransOptionBySubQuestionId(batchId)
    }

    fun getPaperDuration(batchId : String) : LiveData<String>{
        return loginRepo.getPaperDuration(batchId)
    }
}
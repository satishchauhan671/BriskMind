package com.brisk.assessment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.repositories.LoginRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val loginRepo: LoginRepo, loginReq: LoginReq) : ViewModel() {

    init {
        viewModelScope.launch ( Dispatchers.IO){
            loginRepo.login(loginReq.deviceId,loginReq.password,loginReq.userid,loginReq.appVersion,loginReq.appType)
        }
    }

    val loginRes : LiveData<LoginRes>
    get() = loginRepo.loginRes
}
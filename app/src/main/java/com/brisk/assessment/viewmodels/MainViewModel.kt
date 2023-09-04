package com.brisk.assessment.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.repositories.LoginRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val loginRepo: LoginRepo) : ViewModel() {

    init {
        viewModelScope.launch ( Dispatchers.IO){
            loginRepo.login("dddd","ZjFyOUxn","MDgwODIx","MS4wLjA=","com.brisk.assessment")
        }
    }

    val loginRes : LiveData<LoginRes>
    get() = loginRepo.loginRes
}
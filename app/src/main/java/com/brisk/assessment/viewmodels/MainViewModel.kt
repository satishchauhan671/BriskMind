package com.brisk.assessment.viewmodels

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brisk.assessment.common.NetworkResult
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.repositories.LoginRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val loginRepo: LoginRepo) : ViewModel() {

    val loginRes: LiveData<NetworkResult<LoginRes>>
        get() = loginRepo.loginRes

    fun login(loginReq: LoginReq) {
        viewModelScope.launch(Dispatchers.IO) {
            loginRepo.login(
                loginReq.deviceId,
                loginReq.password,
                loginReq.userid,
                loginReq.appVersion,
                loginReq.appType
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
}
package com.brisk.assessment.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.retrofit.NetworkService
import retrofit2.http.Query

class LoginRepo(private val networkService: NetworkService
//, private val briskMindDatabase: BriskMindDatabase
) {

    private val loginResLiveData = MutableLiveData<LoginRes>()

    val loginRes: LiveData<LoginRes>
        get() = loginResLiveData

   suspend fun login(deviceType: String, password: String, userid: String, appVersion: String, appType: String,){
        val result = networkService.login(deviceType,password,userid,appVersion,appType)
        if (result?.body() != null){
           // dataInsertIntoDB(result.body()!!)
            loginResLiveData.postValue(result.body())
        }
    }

    private fun dataInsertIntoDB(res: LoginRes){

    }
}
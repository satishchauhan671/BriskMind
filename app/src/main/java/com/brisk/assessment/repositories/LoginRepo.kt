package com.brisk.assessment.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brisk.assessment.BriskMindApplication
import com.brisk.assessment.common.NetworkResult
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.BriskMindDatabase
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.retrofit.ApiClient
import com.brisk.assessment.retrofit.NetworkService
import org.json.JSONObject
import retrofit2.Response

class LoginRepo(private val application : Application){

    private val networkService = ApiClient.getApiClient()
    private val briskMindDatabase = BriskMindDatabase.getDatabaseInstance(application)
    private val loginResLiveData = MutableLiveData<NetworkResult<LoginRes>>()

    val loginRes: LiveData<NetworkResult<LoginRes>>
    get() = loginResLiveData

    suspend fun login(
        deviceId: String,
        password: String,
        userid: String,
        appVersion: String,
        appType: String,
    ) {
        if (Utility.isInternetConnected(application)){
            loginResLiveData.postValue(NetworkResult.Loading())
            val result = networkService.login(deviceId, password, userid, appVersion, appType)
            handleResponse(result)
        }else{
            loginResLiveData.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    private suspend fun dataInsertIntoDB(res: LoginRes) {
        briskMindDatabase.userDao().insert(res)
        if (res.languages != null && res.languages!!.isNotEmpty()) {
            for (lang in res.languages!!) {
                briskMindDatabase.languageDao().insert(lang)

                if (lang.texts != null) {
                    lang.texts?.language_id = lang.language_id
                    briskMindDatabase.textsDao().insert(lang.texts!!)
                }
            }
        }
    }

    private suspend fun handleResponse(response: Response<LoginRes>) {
        if (response.isSuccessful && response.body() != null) {
            dataInsertIntoDB(response.body()!!)
            loginResLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            loginResLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            loginResLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }
}
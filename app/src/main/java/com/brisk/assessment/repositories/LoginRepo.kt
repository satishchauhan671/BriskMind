package com.brisk.assessment.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brisk.assessment.database.BriskMindDatabase
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.retrofit.NetworkService

class LoginRepo(
    private val networkService: NetworkService, private val briskMindDatabase: BriskMindDatabase
) {

    private val loginResLiveData = MutableLiveData<LoginRes>()

    val loginRes: LiveData<LoginRes>
        get() = loginResLiveData

    suspend fun login(
        deviceId: String,
        password: String,
        userid: String,
        appVersion: String,
        appType: String,
    ) {
        val result = networkService.login(deviceId, password, userid, appVersion, appType)
        if (result?.body() != null) {
            dataInsertIntoDB(result.body()!!)
            loginResLiveData.postValue(result.body())
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
}
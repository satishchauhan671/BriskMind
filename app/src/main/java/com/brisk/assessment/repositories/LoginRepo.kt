package com.brisk.assessment.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brisk.assessment.common.NetworkResult
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.BriskMindDatabase
import com.brisk.assessment.model.ImportAssessmentReq
import com.brisk.assessment.model.ImportAssessmentResponse
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.PaperResponse
import com.brisk.assessment.retrofit.ApiClient
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import org.json.JSONObject
import retrofit2.Response

class LoginRepo(private val application: Application) {

    private val networkService = ApiClient.getApiClient()
    private val briskMindDatabase = BriskMindDatabase.getDatabaseInstance(application)
    private val loginResLiveData = MutableLiveData<NetworkResult<LoginRes>>()
    private val importAssessmentResLiveData =
        MutableLiveData<NetworkResult<ImportAssessmentResponse>>()


    val loginRes: LiveData<NetworkResult<LoginRes>>
        get() = loginResLiveData

    val importAssessmentRes: LiveData<NetworkResult<ImportAssessmentResponse>>
        get() = importAssessmentResLiveData

    suspend fun login(
        loginReq: LoginReq
    ) {
        if (Utility.isInternetConnected(application)) {
            loginResLiveData.postValue(NetworkResult.Loading())
            val result = networkService.login(
                loginReq.deviceId, loginReq.password,
                loginReq.userid, loginReq.appVersion, loginReq.appType, loginReq.loginType
            )
            handleResponse(result)
        } else {
            loginResLiveData.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    suspend fun importAssessment(
        importAssessmentReq: ImportAssessmentReq
    ) {
        if (Utility.isInternetConnected(application)) {
            importAssessmentResLiveData.postValue(NetworkResult.Loading())
            val result = networkService.importAssessment(
                importAssessmentReq.userId, importAssessmentReq.deviceId,
                importAssessmentReq.appUserType, importAssessmentReq.appVersion
            )
            handleImportAssessmentResponse(result)
        } else {
            importAssessmentResLiveData.postValue(NetworkResult.Error("No Internet Connection"))
        }
    }

    private suspend fun dataInsertIntoDB(res: LoginRes) {
        briskMindDatabase.loginDao().insert(res)
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

    private suspend fun handleImportAssessmentResponse(response: Response<ImportAssessmentResponse>) {
        if (response.isSuccessful && response.body() != null) {
            dataInsertIntoDB(response.body()!!)
            importAssessmentResLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            importAssessmentResLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            importAssessmentResLiveData.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    private suspend fun dataInsertIntoDB(res: ImportAssessmentResponse) {
        if (res.batch_array != null && res.batch_array!!.size > 0) {
            briskMindDatabase.batchDao().insert(res.batch_array!!)

            for (batch in res.batch_array!!) {
                if (batch.batch_config != null) {
                    briskMindDatabase.batchConfigDao().insert(batch.batch_config!!)
                }
            }
        }

        if (res.user_array != null && res.user_array!!.size > 0) {
            briskMindDatabase.userDao().insertAll(res.user_array!!)
        }

        if (res.lang_record != null && res.lang_record!!.size > 0) {
            briskMindDatabase.importLanguageDao().insert(res.lang_record!!)
        }

        if (res.feedback_array != null && res.feedback_array!!.size > 0) {
            briskMindDatabase.feedbackDao().insert(res.feedback_array!!)
        }

        if (res.paper_array != null && res.paper_array!!.isNotEmpty()) {
            for (item in res.paper_array!!) {
                if (item is LinkedTreeMap<*, *>) {
                    val paperResponse = convertToPaperResponse(item)
                    briskMindDatabase.paperDao().insert(paperResponse)

                    if (paperResponse.sub_questions != null) {
                        briskMindDatabase.subQuestionsDao().insert(paperResponse.sub_questions!!)
                    }
                }
            }
        }
    }
    fun convertToPaperResponse(item: LinkedTreeMap<*, *>): PaperResponse {
        // Use Gson to convert LinkedTreeMap to JSON string, and then parse it as PaperResponse
        val gson = Gson()
        val json = gson.toJson(item)
        return gson.fromJson(json, PaperResponse::class.java)
    }
}
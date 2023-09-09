package com.brisk.assessment.retrofit

import com.brisk.assessment.model.ImportAssessmentResponse
import com.brisk.assessment.model.LoginRes
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface NetworkService {

    @POST("login.php/{device_id}/{password}/{userid}/{appVersion}/{app_type}")
    suspend fun login(
        @Query("device_id") deviceId: String,
        @Query("password") password: String,
        @Query("userid") userid: String,
        @Query("appVersion") appVersion: String,
        @Query("app_type") appType: String,
        @Query("login_type") loginType: String,
    ): Response<LoginRes>

    @POST("import_assessment.php/{user_id}/{device_id}/{app_user_type}/{appVersion}")
    suspend fun importAssessment(
        @Query("user_id") userId: String,
        @Query("device_id") deviceId: String,
        @Query("app_user_type") appUserType: String,
        @Query("appVersion") appVersion: String
    ): Response<ImportAssessmentResponse>
}
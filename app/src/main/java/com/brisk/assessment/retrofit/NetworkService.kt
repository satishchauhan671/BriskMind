package com.brisk.assessment.retrofit

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
    ): Response<LoginRes>
}
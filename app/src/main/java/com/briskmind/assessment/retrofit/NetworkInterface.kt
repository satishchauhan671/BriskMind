package com.briskmind.assessment.retrofit

import com.briskmind.assessment.model.LoginResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkInterface {

    @GET("login.php/{device_id}/{password}/{userid}/{appVersion}/{app_type}")
    fun login(@Path("device_id") deviceId : String,@Path("password") password : String
              ,@Path("userid") userid : String,@Path("appVersion") appVersion : String
              ,@Path("app_type") app_type : String) : Response<LoginResponse>
}
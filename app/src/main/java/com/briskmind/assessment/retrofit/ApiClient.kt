package com.briskmind.assessment.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        val BASE_URL: String = "https://43.205.174.23/android_api/"
        private var retrofit: Retrofit? = null

        fun getApiClient(): NetworkInterface? {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit!!.create(NetworkInterface::class.java)
        }
    }
}
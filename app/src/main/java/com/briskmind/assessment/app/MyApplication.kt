package com.inventia.ugo_mici.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MyApplication : Application() {

    val CHANNEL_ID = "Service code Running"

    // private RequestQueue mRequestQueue;
    private var mRetrofitLogin: Retrofit? = null
    private var mRetrofitOCR: Retrofit? = null
    private var mRetrofitIAM: Retrofit? = null



    override fun onCreate() {
        super.onCreate()
        mInstance = this
        createNotificationChannel()
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(200, TimeUnit.SECONDS)
        .writeTimeout(200, TimeUnit.SECONDS)
        .readTimeout(200, TimeUnit.SECONDS)
        .build()


    val retrofitInstanceLogin: Retrofit?
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (mRetrofitLogin == null) {
                mRetrofitLogin = Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
            }
            return mRetrofitLogin
        }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val seviceChannel = NotificationChannel(
                MyApplication.CHANNEL_ID,
                "My Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(seviceChannel)
        }
    }

    companion object {
        val TAG = MyApplication::class.java.simpleName
        const val CHANNEL_ID = "CIServiceChannel"

        @get:Synchronized
        lateinit var mInstance: MyApplication
            private set

    }
}
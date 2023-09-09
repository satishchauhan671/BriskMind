package com.brisk.assessment

import android.app.Application
import android.os.Build

class BriskMindApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        appPackageName = this.packageName
        versionName = BuildConfig.VERSION_NAME
        deviceId = Build.DEVICE

    }

    companion object {
        lateinit var mInstance: BriskMindApplication
        lateinit var appPackageName : String
        lateinit var versionName : String
        lateinit var deviceId : String

        fun getInstance() : BriskMindApplication{
            return mInstance
        }
    }
}
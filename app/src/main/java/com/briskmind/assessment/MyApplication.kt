package com.briskmind.assessment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object {

        private lateinit var mInstance: MyApplication

        fun getInstance() : MyApplication{
            return mInstance
        }
    }
}
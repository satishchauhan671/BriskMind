package com.brisk.assessment

import android.app.Application
import com.brisk.assessment.database.BriskMindDatabase
import com.brisk.assessment.repositories.LoginRepo
import com.brisk.assessment.retrofit.ApiClient

class BriskMindApplication : Application() {

    lateinit var loginRepo: LoginRepo

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        initialize()
    }

    private fun initialize(){
        val networkService = ApiClient.getApiClient()
        val database = BriskMindDatabase.getDatabaseInstance(applicationContext)
        loginRepo = LoginRepo(networkService,database)
    }

    companion object {
        lateinit var mInstance: BriskMindApplication

        fun getInstance() : BriskMindApplication{
            return mInstance
        }
    }
}
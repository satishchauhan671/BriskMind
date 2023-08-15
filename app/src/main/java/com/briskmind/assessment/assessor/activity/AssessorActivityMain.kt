package com.briskmind.assessment.assessor.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.briskmind.assessment.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssessorActivityMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessor_main)
    }
}
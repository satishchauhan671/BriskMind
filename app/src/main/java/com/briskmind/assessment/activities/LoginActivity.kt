package com.briskmind.assessment.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.briskmind.assessment.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        val window: Window = window
//        val decorView: View = window.decorView
//        val wic = WindowInsetsControllerCompat(window, decorView)
//        wic.isAppearanceLightStatusBars = false  // true or false as desired.

       }

}
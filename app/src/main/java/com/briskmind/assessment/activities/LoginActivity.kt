package com.briskmind.assessment.activities

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import com.briskmind.assessment.R
import com.briskmind.assessment.common.Utility
import com.briskmind.assessment.databinding.ActivityLoginBinding
import com.briskmind.assessment.fragments.LoginFragment
import com.briskmind.assessment.listner.CheckIsOnline

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private var loginFragment: LoginFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window: Window = window
            val decorView: View = window.getDecorView()
            val wic = WindowInsetsControllerCompat(window, decorView)
            wic.isAppearanceLightStatusBars = true // true or false as desired.
        }

        loginFragment = LoginFragment()
        Utility.addFragment(LoginFragment(), supportFragmentManager, R.id.layout_root)
    }


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return try {
            if (event.action == MotionEvent.ACTION_DOWN) {
                val v = currentFocus
                if (v is EditText) {
                    val outRect = Rect()
                    v.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        v.clearFocus()
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm?.hideSoftInputFromWindow(v.getWindowToken(), 0)
                    }
                }
            }
            super.dispatchTouchEvent(event)
        } catch (e: Exception) {
            false
        }
    }

    override fun onResume() {
        if (ContextCompat.checkSelfPermission(
                this@LoginActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this@LoginActivity,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        } else {
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this@LoginActivity,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this@LoginActivity,
                        arrayOf<String>(Manifest.permission.POST_NOTIFICATIONS),
                        22
                    )
                }
            }*/

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@LoginActivity,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                        arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.POST_NOTIFICATIONS
                        ), 101
                    )
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                        arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.POST_NOTIFICATIONS
                        ), 101
                    )
                }
            }

        }

        super.onResume()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  getLocation()
            }
        }
    }



}
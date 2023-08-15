package com.briskmind.assessment.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.briskmind.assessment.R
import com.briskmind.assessment.common.Utility
import com.briskmind.assessment.databinding.ActivityLoginBinding
import com.briskmind.assessment.fragments.LoginFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        Utility.addFragment(LoginFragment(),supportFragmentManager,binding.layoutRoot.id)
       }

}
package com.brisk.assessment.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.brisk.assessment.assessor.fragment.AssessorLoginFragment
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        Utility.addFragment(AssessorLoginFragment(),supportFragmentManager,binding.layoutRoot.id)
       }

}
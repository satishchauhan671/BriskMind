package com.briskmind.assessment.assessor.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.briskmind.assessment.assessor.fragment.AssessorMainFragment
import com.briskmind.assessment.common.Utility
import com.briskmind.assessment.databinding.ActivityAssessorMainBinding

class AssessorActivityMain : AppCompatActivity() {

    private lateinit var binding : ActivityAssessorMainBinding
    private lateinit var view : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessorMainBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)


        Utility.replaceFragment(AssessorMainFragment(),supportFragmentManager,binding.layAssessor.id)
    }
}
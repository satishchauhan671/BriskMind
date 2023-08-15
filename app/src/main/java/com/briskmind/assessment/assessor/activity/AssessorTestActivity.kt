package com.briskmind.assessment.assessor.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.briskmind.assessment.assessor.adapter.AssessorTestPageNoAdapter
import com.briskmind.assessment.databinding.ActivityAssessorTestBinding

class AssessorTestActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAssessorTestBinding
    private lateinit var view : View
    private lateinit var assessorTestPageNoAdapter: AssessorTestPageNoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessorTestBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        assessorTestPageNoAdapter = AssessorTestPageNoAdapter(this,supportFragmentManager)
        binding.assessorTestPageNoRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.assessorTestPageNoRv.adapter = assessorTestPageNoAdapter

    }
}
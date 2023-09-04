package com.brisk.assessment.assessor.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.brisk.assessment.assessor.adapter.AssessorTestPageNoAdapter
import com.brisk.assessment.databinding.ActivityAssessorTestBinding

class AssessorTestActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAssessorTestBinding
    private lateinit var view: View
    private lateinit var assessorTestPageNoAdapter: AssessorTestPageNoAdapter
    private var isVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessorTestBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        assessorTestPageNoAdapter = AssessorTestPageNoAdapter(this, supportFragmentManager)
        binding.assessorTestPageNoRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.assessorTestPageNoRv.adapter = assessorTestPageNoAdapter
        binding.assessorTestLay.setOnClickListener(this)
        binding.toolbar.instructionDialogIv.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.toolbar.instructionDialogIv -> {
                if (isVisible) {
                    binding.cardInstruction.visibility = View.GONE
                    binding.rlDimBg.visibility = View.GONE
                    isVisible = false
                } else {
                    binding.cardInstruction.visibility = View.VISIBLE
                    binding.rlDimBg.visibility = View.VISIBLE
                    isVisible = true
                }

            }

            binding.cardInstruction,
            binding.assessorTestLay -> {
                if (isVisible) {
                    binding.cardInstruction.visibility = View.GONE
                    binding.rlDimBg.visibility = View.GONE
                    isVisible = false
                }
            }
        }
    }

}
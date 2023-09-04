package com.brisk.assessment.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.brisk.assessment.adapter.StudentTestPageNoAdapter
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.ActivityStudentTestBinding
import com.brisk.assessment.fragments.StudentImagesFragments

class StudentTestActivity  : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityStudentTestBinding
    private lateinit var view: View
    private lateinit var studentTestPageNoAdapter: StudentTestPageNoAdapter
    private var isVisible = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentTestBinding.inflate(layoutInflater)
        view = binding.root
        setContentView(view)

        binding.toolbar.recordView.visibility=View.GONE

        binding.toolbar.submitTestTv.setOnClickListener(this)
        studentTestPageNoAdapter = StudentTestPageNoAdapter(this, supportFragmentManager)
        binding.studentTestPageNoRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.studentTestPageNoRv.adapter = studentTestPageNoAdapter
        binding.studentTestLay.setOnClickListener(this)
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
            binding.studentTestLay -> {
                if (isVisible) {
                    binding.cardInstruction.visibility = View.GONE
                    binding.rlDimBg.visibility = View.GONE
                    isVisible = false
                }
            }

            binding.toolbar.submitTestTv -> {
                Utility.replaceFragment(StudentImagesFragments("Final"),supportFragmentManager, binding.studentTestLay.id)
            }
        }
    }

}
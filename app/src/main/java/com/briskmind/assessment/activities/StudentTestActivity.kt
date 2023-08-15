package com.briskmind.assessment.activities

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import com.briskmind.assessment.R
import com.briskmind.assessment.adapter.StudentTestPageNoAdapter
import com.briskmind.assessment.assessor.adapter.AssessorTestPageNoAdapter
import com.briskmind.assessment.databinding.ActivityAssessorTestBinding
import com.briskmind.assessment.databinding.ActivityStudentTestBinding

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

        Handler().postDelayed({ popupWindow() }, 100)


        studentTestPageNoAdapter = StudentTestPageNoAdapter(this, supportFragmentManager)
        binding.studentTestPageNoRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.studentTestPageNoRv.adapter = studentTestPageNoAdapter
        binding.studentTestLay.setOnClickListener(this)
        binding.toolbar.instructionDialogIv.setOnClickListener(this)
    }


    @SuppressLint("MissingInflatedId")
    private fun popupWindow() {
        val inflater = getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_student_id_profile_layout, null)

        // step 2
        val wid = LinearLayout.LayoutParams.MATCH_PARENT
        val high = LinearLayout.LayoutParams.MATCH_PARENT
        val focus = true
        val popupWindow = PopupWindow(popupView, wid, high, focus)

        val close: LinearLayout = popupView.findViewById(R.id.btnSaveNext)

        close.setOnClickListener { popupWindow.dismiss() }

        // step 3
        popupWindow.showAtLocation(binding.studentTestLay, Gravity.CENTER, 0, 0)
        popupWindow.isOutsideTouchable=false
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
        }
    }

}
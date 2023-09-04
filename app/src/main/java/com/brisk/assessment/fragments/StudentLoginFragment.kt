package com.brisk.assessment.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.brisk.assessment.R
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.StudentLoginLayoutBinding

class StudentLoginFragment: Fragment(), View.OnClickListener {
    private var _binding : StudentLoginLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StudentLoginLayoutBinding.inflate(inflater, container, false)

        binding.btnStudentSignIn.setOnClickListener(this)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onClick(p0: View?) {
        when (p0) {
            _binding!!.btnStudentSignIn-> {
                Utility.replaceFragment(StudentImagesFragments("Start"),mActivity.supportFragmentManager, R.id.layout_root)
            }
        }
    }

}
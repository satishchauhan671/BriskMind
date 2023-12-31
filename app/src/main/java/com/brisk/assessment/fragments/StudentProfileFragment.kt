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
import com.brisk.assessment.databinding.StudentProfileFragmentBinding

class StudentProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: StudentProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = StudentProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveProfile.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onClick(p0: View?) {
        when (p0) {

            binding.btnSaveProfile -> {

                    Utility.replaceFragment(
                        StudentListFragment(),
                        mActivity.supportFragmentManager,
                        R.id.layout_root
                    )
            }
        }
    }
}
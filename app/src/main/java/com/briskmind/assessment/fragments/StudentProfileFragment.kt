package com.briskmind.assessment.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.briskmind.assessment.R
import com.briskmind.assessment.common.Utility
import com.briskmind.assessment.databinding.StudentProfileFragmentBinding

class StudentProfileFragment : Fragment, View.OnClickListener {

    lateinit var type: String
    private var _binding: StudentProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity

    constructor()
    constructor(type: String) {
        this.type = type
    }

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
                if (type.equals("Start")) {
                    Utility.replaceFragment(
                        StudentListFragment(),
                        mActivity.supportFragmentManager,
                        R.id.layout_root
                    )
                } else {
                    Utility.replaceFragment(
                        StudentFeedbackFragment(),
                        mActivity.supportFragmentManager,
                        R.id.layout_root
                    )
                }
            }
        }
    }
}
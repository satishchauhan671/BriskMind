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
import com.briskmind.assessment.databinding.StudentIdProfileLayoutBinding

class StudentImagesFragments : Fragment, View.OnClickListener {
    private var _binding: StudentIdProfileLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity
    private var type : String =""
    constructor()

    constructor(type : String)
    {
        this.type = type
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = StudentIdProfileLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLoginNow.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onClick(p0: View?) {
        when (p0) {

            binding.buttonLoginNow -> {

                if (type.equals("Start"))
                {
                    Utility.replaceFragment(StudentProfileFragment(),mActivity.supportFragmentManager, R.id.layout_root)
                }
                else
                {
                    Utility.replaceFragment(StudentFeedbackFragment(),mActivity.supportFragmentManager, R.id.layout_root)
                }

            }
        }
    }
}
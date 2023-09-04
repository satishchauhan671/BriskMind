package com.brisk.assessment.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.brisk.assessment.assessor.fragment.AssessorLoginFragment
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signStudent.setOnClickListener(this)
        binding.signAssessor.setOnClickListener(this)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.signAssessor -> {
                Utility.replaceFragment(AssessorLoginFragment(),mActivity.supportFragmentManager, binding.layoutRoot.id)
            }

            binding.signStudent -> {
                Utility.replaceFragment(StudentLoginFragment(),mActivity.supportFragmentManager, binding.layoutRoot.id)
            }
        }
    }

}
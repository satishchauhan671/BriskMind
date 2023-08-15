package com.briskmind.assessment.assessor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.briskmind.assessment.R
import com.briskmind.assessment.databinding.AssessorLoginLayoutBinding

class AssessorLoginFragment : Fragment(), View.OnClickListener {

    private var _binding : AssessorLoginLayoutBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AssessorLoginLayoutBinding.inflate(inflater, container, false)
        binding.txtSignInAssessor.setOnClickListener(this)
        binding.signInLayAssessor.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.txtSignInAssessor,
            binding.signInLayAssessor -> {
                findNavController().navigate(R.id.action_assessor_login_to_assessor_login_image)
            }
        }
    }


}
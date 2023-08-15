package com.briskmind.assessment.assessor.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briskmind.assessment.databinding.FragmentAssessorMainBinding

class AssessorMainFragment : Fragment() {

    private var _binding: FragmentAssessorMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssessorMainBinding.inflate(inflater, container, false)

        return binding.root
    }
}
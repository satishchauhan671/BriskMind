package com.briskmind.assessment.assessor.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.briskmind.assessment.assessor.activity.AssessorActivityMain
import com.briskmind.assessment.databinding.FragmentAssessorLoginImageBinding

class AssessorLoginImageFragment : Fragment(), View.OnClickListener {

    private var _binding : FragmentAssessorLoginImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssessorLoginImageBinding.inflate(inflater, container, false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardSaveNext.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0){
            binding.cardSaveNext -> {
                val intent = Intent(context, AssessorActivityMain::class.java)
                startActivity(intent)
            }
        }
    }
}
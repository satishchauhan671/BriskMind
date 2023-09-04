package com.brisk.assessment.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.brisk.assessment.activities.StudentTestActivity
import com.brisk.assessment.databinding.InstructionLayoutBinding

class InstructionFragment: Fragment(), View.OnClickListener {
    private var _binding: InstructionLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = InstructionLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLoginNow.setOnClickListener(this)
        binding.buttonLogin.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.buttonLogin,
            binding.buttonLoginNow -> {
                val intent = Intent(context, StudentTestActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
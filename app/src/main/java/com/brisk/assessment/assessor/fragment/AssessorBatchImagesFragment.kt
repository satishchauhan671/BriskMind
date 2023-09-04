package com.brisk.assessment.assessor.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.brisk.assessment.databinding.BatchImagesLayoutBinding

class AssessorBatchImagesFragment : Fragment(), View.OnClickListener{

    private lateinit var _binding : BatchImagesLayoutBinding
    private val binding get() = _binding
    private lateinit var mActivity : FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BatchImagesLayoutBinding.inflate(inflater, container, false)
        binding.toolbar.backArrowIv.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.pageTitle.text = "Batch Image"
    }

    override fun onClick(p0: View?) {
        when(p0){
            binding.toolbar.backArrowIv -> {
                    mActivity.onBackPressed()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }
}
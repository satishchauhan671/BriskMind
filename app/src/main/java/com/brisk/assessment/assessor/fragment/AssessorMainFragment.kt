package com.brisk.assessment.assessor.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.brisk.assessment.assessor.adapter.AssessorMainAdapter
import com.brisk.assessment.assessor.listener.BatchImageListener
import com.brisk.assessment.assessor.listener.ChooseAssessorMainListener
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.FragmentAssessorMainBinding

class AssessorMainFragment : Fragment() {

    private var _binding: FragmentAssessorMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var assessorMainAdapter: AssessorMainAdapter
    private lateinit var mActivity: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssessorMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        assessorMainAdapter = AssessorMainAdapter(mActivity,mActivity.supportFragmentManager)
        binding.assessorTestRv.layoutManager = LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false)
        assessorMainAdapter.setAdapterListener(chooseMainListener)
        assessorMainAdapter.setBatchImageAdapterListener(batchImageListener)
        binding.assessorTestRv.adapter = assessorMainAdapter

    }

    private val chooseMainListener = object : ChooseAssessorMainListener{
        override fun chooseMemberAdapterListener(pos: Int, id: Int) {
            Utility.replaceFragment(AssessorIdProfileImageFragment(), mActivity.supportFragmentManager, binding.layoutRoot.id)
        }

    }

    private val batchImageListener = object : BatchImageListener{
        override fun batchImageAdapterListener(pos: Int, id: Int, visible: Boolean) {

            if (id == 0){
                if (visible){
                    binding.dimOverlay.root.visibility = View.VISIBLE
                } else{
                    binding.dimOverlay.root.visibility = View.GONE
                }
            }else {
                Utility.replaceFragment(
                    AssessorBatchImagesFragment(),
                    mActivity.supportFragmentManager,
                    binding.layoutRoot.id
                )
            }
        }
    }
}
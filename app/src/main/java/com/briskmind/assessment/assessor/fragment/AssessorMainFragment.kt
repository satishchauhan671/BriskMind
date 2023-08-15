package com.briskmind.assessment.assessor.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.briskmind.assessment.R
import com.briskmind.assessment.assessor.adapter.AssessorMainAdapter
import com.briskmind.assessment.assessor.listener.ChooseAssessorMainListener
import com.briskmind.assessment.common.Utility
import com.briskmind.assessment.databinding.FragmentAssessorMainBinding

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
        binding.assessorTestRv.adapter = assessorMainAdapter

    }

    private val chooseMainListener = object : ChooseAssessorMainListener{
        override fun chooseMemberAdapterListener(pos: Int, id: Int) {
            Utility.replaceFragment(AssessorIdProfileImageFragment(), mActivity.supportFragmentManager, binding.layAssessor.id)
        }

    }
}
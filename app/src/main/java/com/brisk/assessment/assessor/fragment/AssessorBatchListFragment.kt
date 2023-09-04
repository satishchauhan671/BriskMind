package com.brisk.assessment.assessor.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.brisk.assessment.assessor.adapter.AssessorBatchWiseAdapter
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.FragmentAssessorListBatchWiseBinding
import com.brisk.assessment.listner.ChooseStudentListListener

class AssessorBatchListFragment  : Fragment() , View.OnClickListener{
    private var _binding: FragmentAssessorListBatchWiseBinding? = null
    private val binding get() = _binding!!
    private lateinit var studentListAdapter: AssessorBatchWiseAdapter
    private lateinit var mActivity: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssessorListBatchWiseBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.syncBatch.setOnClickListener(this)
        studentListAdapter = AssessorBatchWiseAdapter(mActivity,mActivity.supportFragmentManager)
        binding.recyclerViewStudent.layoutManager = LinearLayoutManager(mActivity,
            LinearLayoutManager.VERTICAL,false)
        studentListAdapter.setAdapterListener(chooseMainListener)
        binding.recyclerViewStudent.adapter = studentListAdapter

    }

    private val chooseMainListener = object : ChooseStudentListListener {
        override fun chooseMemberAdapterListener(pos: Int, id: Int) {
            Utility.replaceFragment(AssessorAttendanceFragment(),mActivity.supportFragmentManager, binding.layoutRoot.id)
        }

    }

    override fun onClick(p0: View?) {
        when (p0){
            binding.syncBatch -> {
                Utility.replaceFragment(AssessorFeedbackFragment(), mActivity.supportFragmentManager, binding.layoutRoot.id)
            }
        }
    }

}
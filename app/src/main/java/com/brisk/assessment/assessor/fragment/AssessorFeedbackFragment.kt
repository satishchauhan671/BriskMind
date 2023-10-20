package com.brisk.assessment.assessor.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.brisk.assessment.adapter.StudentFeedbackAdapter
import com.brisk.assessment.assessor.adapter.AssessorFeedbackAdapter
import com.brisk.assessment.assessor.adapter.AssessorMainAdapter
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.FeedbackDataHelper
import com.brisk.assessment.databinding.FragmentFeedbackBinding
import com.brisk.assessment.fragments.InstructionFragment
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse

class AssessorFeedbackFragment : Fragment() {
    private var _binding: FragmentFeedbackBinding? = null
    private val binding get() = _binding!!
    private lateinit var assessorFeedbackAdapter: AssessorFeedbackAdapter
    private lateinit var mActivity: FragmentActivity
    private lateinit var feedbackRes: ArrayList<FeedbackResponse>
    private var bundle: Bundle? = null
    var batchId = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedbackBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bundle = arguments
        if (bundle != null) {
            batchId = bundle!!.getString(Constants.batchId)!!
        }

        bindFeedbackData()
    }

    private val chooseMainListener = object : ChooseStudentListListener {
        override fun chooseMemberAdapterListener(pos: Int, id: Int) {
            Utility.replaceFragment(
                InstructionFragment(),
                mActivity.supportFragmentManager,
                binding.layoutRoot.id
            )
        }

    }

    private fun bindFeedbackData() {

        feedbackRes = FeedbackDataHelper.getFeedbackData(mActivity)

        binding.toolbar.pageTitle.text = "Feedback"
        assessorFeedbackAdapter =
            AssessorFeedbackAdapter(mActivity, mActivity.supportFragmentManager, feedbackRes,batchId)
        binding.feedbackRv.layoutManager = LinearLayoutManager(
            mActivity,
            LinearLayoutManager.VERTICAL, false
        )
        assessorFeedbackAdapter.setAdapterListener(chooseMainListener)
        binding.feedbackRv.adapter = assessorFeedbackAdapter
    }

}
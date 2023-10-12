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
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.FragmentFeedbackBinding
import com.brisk.assessment.fragments.InstructionFragment
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.repositories.LoginRepo
import com.brisk.assessment.viewmodels.MainViewModel
import com.brisk.assessment.viewmodels.MainViewModelFactory

class AssessorFeedbackFragment  : Fragment() {
    private var _binding: FragmentFeedbackBinding? = null
    private val binding get() = _binding!!
    private lateinit var assessorFeedbackAdapter: AssessorFeedbackAdapter
    private lateinit var mActivity: FragmentActivity
    private lateinit var repo: LoginRepo
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var feedbackRes: List<FeedbackResponse>
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

        repo = LoginRepo(mActivity.application)

        // initialize model factory
        mainViewModelFactory = MainViewModelFactory(repo)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        bindFeedbackData()
    }

    private val chooseMainListener = object : ChooseStudentListListener {
        override fun chooseMemberAdapterListener(pos: Int, id: Int) {
            Utility.replaceFragment(InstructionFragment(), mActivity.supportFragmentManager, binding.layoutRoot.id)
        }

    }

    private fun bindFeedbackData(){
        mainViewModel.getFeedbackList().observe(viewLifecycleOwner){
            feedbackRes = it

            println(feedbackRes.toString())

            binding.toolbar.pageTitle.text = "Feedback"
            assessorFeedbackAdapter = AssessorFeedbackAdapter(mActivity,mActivity.supportFragmentManager , feedbackRes)
            binding.feedbackRv.layoutManager = LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL,false)
            assessorFeedbackAdapter.setAdapterListener(chooseMainListener)
            binding.feedbackRv.adapter = assessorFeedbackAdapter
        }
    }

}
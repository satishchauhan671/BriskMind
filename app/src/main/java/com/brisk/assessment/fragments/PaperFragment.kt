package com.brisk.assessment.fragments

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
import com.brisk.assessment.assessor.activity.AssessorTestActivity
import com.brisk.assessment.assessor.adapter.AssessorBatchWiseAdapter
import com.brisk.assessment.assessor.adapter.PagerAdapter
import com.brisk.assessment.assessor.adapter.PaperAdapter
import com.brisk.assessment.assessor.fragment.AssessorAttendanceFragment
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.AdapterPaperBinding
import com.brisk.assessment.databinding.FragmentPaperBinding
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.PaperResponse
import com.brisk.assessment.model.SubQuestionResponse
import com.brisk.assessment.repositories.LoginRepo
import com.brisk.assessment.viewmodels.MainViewModel
import com.brisk.assessment.viewmodels.MainViewModelFactory

class PaperFragment(paperList : List<PaperResponse>, assessorTestActivity: AssessorTestActivity)  : Fragment() {

     private val paperList : List<PaperResponse> = paperList

    private val assessorTestActivity : AssessorTestActivity = assessorTestActivity

    private lateinit var _binding : FragmentPaperBinding
    private val binding get() = _binding
    private lateinit var mActivity: FragmentActivity
    lateinit var paperAdapter: PaperAdapter
    private lateinit var repo: LoginRepo
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    lateinit var subQuestionList : List<SubQuestionResponse>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaperBinding.inflate(inflater,container,false)
        repo = LoginRepo(mActivity.application)

        // initialize model factory
        mainViewModelFactory = MainViewModelFactory(repo)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        assessorTestActivity.setPaperListener(pageChangeListener)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        assessorTestActivity.setPaperListener(pageChangeListener)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    private val chooseMainListener = object : ChooseStudentListListener {
        override fun chooseMemberAdapterListener(pos: Int, id: Int) {
        }

    }

    private val pageChangeListener = object : AssessorTestActivity.PagerListener {
        override fun pagerChangeListener(position: Int, paperResponse: PaperResponse) {

            if (!paperResponse.question.isNullOrEmpty()) {
                binding.questionTv.text = paperResponse.question
            }

            if (!paperResponse.question_id.isNullOrEmpty()) {
                bindSubQueListData(paperResponse.question_id!!)
            }

        }
    }

    private fun bindSubQueListData(queId: String){
        mainViewModel.getSubQueByQueId(queId).observe(this){
            subQuestionList = it

            if (subQuestionList.isNotEmpty()) {
                paperAdapter = PaperAdapter(
                    mActivity,
                    mActivity.supportFragmentManager,
                    subQuestionList,
                    mainViewModel
                )
                binding.paperRv.layoutManager = LinearLayoutManager(
                    mActivity,
                    LinearLayoutManager.VERTICAL, false
                )
                paperAdapter.setAdapterListener(chooseMainListener)
                binding.paperRv.adapter = paperAdapter
            }
        }
    }


}
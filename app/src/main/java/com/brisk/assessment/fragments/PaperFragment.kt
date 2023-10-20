package com.brisk.assessment.fragments

import android.annotation.SuppressLint
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
import com.brisk.assessment.assessor.adapter.PaperAdapter
import com.brisk.assessment.database.SubQuestionDataHelper
import com.brisk.assessment.databinding.FragmentPaperBinding
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.PaperResponse
import com.brisk.assessment.model.SubQuestionResponse

class PaperFragment(assessorTestActivity: AssessorTestActivity)  : Fragment() {

    private val assessorTestActivity : AssessorTestActivity = assessorTestActivity

    private lateinit var _binding : FragmentPaperBinding
    private val binding get() = _binding
    private lateinit var mActivity: FragmentActivity
    lateinit var paperAdapter: PaperAdapter
    lateinit var subQuestionList : ArrayList<SubQuestionResponse>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaperBinding.inflate(inflater,container,false)

        assessorTestActivity.setPaperListener(pageChangeListener)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    @SuppressLint("SuspiciousIndentation")
    private fun bindSubQueListData(queId: String){

        subQuestionList= SubQuestionDataHelper.getSubQuestionByQueId(queId,mActivity)


            if (subQuestionList.isNotEmpty()) {
                paperAdapter = PaperAdapter(
                    mActivity,
                    mActivity.supportFragmentManager,
                    subQuestionList
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
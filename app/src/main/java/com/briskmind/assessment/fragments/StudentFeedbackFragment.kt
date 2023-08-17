package com.briskmind.assessment.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.briskmind.assessment.adapter.StudentFeedbackAdapter
import com.briskmind.assessment.common.Utility
import com.briskmind.assessment.databinding.FragmentFeedbackBinding
import com.briskmind.assessment.listner.ChooseStudentListListener

class StudentFeedbackFragment  : Fragment() {
    private var _binding: FragmentFeedbackBinding? = null
    private val binding get() = _binding!!
    private lateinit var studentListAdapter: StudentFeedbackAdapter
    private lateinit var mActivity: FragmentActivity

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

        studentListAdapter = StudentFeedbackAdapter(mActivity,mActivity.supportFragmentManager)
        binding.feedbackRv.layoutManager = LinearLayoutManager(mActivity,
            LinearLayoutManager.VERTICAL,false)
        studentListAdapter.setAdapterListener(chooseMainListener)
        binding.feedbackRv.adapter = studentListAdapter

    }

    private val chooseMainListener = object : ChooseStudentListListener {
        override fun chooseMemberAdapterListener(pos: Int, id: Int) {
            Utility.replaceFragment(InstructionFragment(), mActivity.supportFragmentManager, binding.layoutRoot.id)
        }

    }

}
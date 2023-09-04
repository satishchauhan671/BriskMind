package com.brisk.assessment.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.brisk.assessment.adapter.StudentListAdapter
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.FragmentStudentListBinding
import com.brisk.assessment.listner.ChooseStudentListListener

class StudentListFragment  : Fragment() {
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var studentListAdapter: StudentListAdapter
    private lateinit var mActivity: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentListAdapter = StudentListAdapter(mActivity,mActivity.supportFragmentManager)
        binding.recyclerViewStudent.layoutManager = LinearLayoutManager(mActivity,
            LinearLayoutManager.VERTICAL,false)
        studentListAdapter.setAdapterListener(chooseMainListener)
        binding.recyclerViewStudent.adapter = studentListAdapter

    }

    private val chooseMainListener = object : ChooseStudentListListener {
        override fun chooseMemberAdapterListener(pos: Int, id: Int) {
            Utility.replaceFragment(InstructionFragment(), mActivity.supportFragmentManager, binding.lytStudent.id)
        }

    }

}
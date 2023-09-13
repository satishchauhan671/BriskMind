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
import com.brisk.assessment.assessor.adapter.AssessorBatchWiseAdapter
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.FragmentAssessorListBatchWiseBinding
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.UserResponse
import com.brisk.assessment.repositories.LoginRepo
import com.brisk.assessment.viewmodels.MainViewModel
import com.brisk.assessment.viewmodels.MainViewModelFactory

class AssessorBatchListFragment  : Fragment() , View.OnClickListener{
    private var _binding: FragmentAssessorListBatchWiseBinding? = null
    private val binding get() = _binding!!
    private lateinit var studentListAdapter: AssessorBatchWiseAdapter
    private lateinit var mActivity: FragmentActivity
    var batchId = ""
    var batchNo = ""
    private lateinit var repo: LoginRepo
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var userList : List<UserResponse>

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
        repo = LoginRepo(mActivity.application)

        // initialize model factory
        mainViewModelFactory = MainViewModelFactory(repo)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        binding.syncBatch.setOnClickListener(this)


        val bundle = arguments
        if (bundle != null && !bundle.isEmpty) {
            batchId = bundle.getString(Constants.batchId).toString()
            batchNo = bundle.getString(Constants.batchNo).toString()

            binding.batchIdTv.text = batchId
            binding.batchNameTv.text = batchNo
        }


        bindBatchListData()
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


    private fun bindBatchListData(){
        mainViewModel.getUserByBatchId(batchId).observe(viewLifecycleOwner){
            userList = it

            println(userList.toString())

            studentListAdapter = AssessorBatchWiseAdapter(mActivity,mActivity.supportFragmentManager, userList)
            binding.recyclerViewStudent.layoutManager = LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL,false)
            studentListAdapter.setAdapterListener(chooseMainListener)
            binding.recyclerViewStudent.adapter = studentListAdapter

        }
    }
}
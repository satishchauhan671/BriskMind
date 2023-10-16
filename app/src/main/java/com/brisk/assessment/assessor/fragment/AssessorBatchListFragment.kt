package com.brisk.assessment.assessor.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.brisk.assessment.assessor.adapter.AssessorBatchWiseAdapter
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.FragmentAssessorListBatchWiseBinding
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.BatchConfigRes
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.ImportLanguageResponse
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SyncBatchArray
import com.brisk.assessment.model.UserResponse
import com.brisk.assessment.repositories.AssessorRepo
import com.brisk.assessment.repositories.LoginRepo
import com.brisk.assessment.viewmodels.AssessorViewModel
import com.brisk.assessment.viewmodels.AssessorViewModelFactory
import com.brisk.assessment.viewmodels.MainViewModel
import com.brisk.assessment.viewmodels.MainViewModelFactory
import kotlinx.coroutines.runBlocking

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
    private lateinit var batchRes : BatchRes
    private lateinit var batchConfigRes: BatchConfigRes
    private lateinit var assessorRepo : AssessorRepo
    private lateinit var assessorViewModel : AssessorViewModel
    private lateinit var assessorViewModelFactory : AssessorViewModelFactory
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

        assessorRepo = AssessorRepo(mActivity.application)
        assessorViewModelFactory=AssessorViewModelFactory(assessorRepo)
        assessorViewModel = ViewModelProvider(mActivity, AssessorViewModelFactory(assessorRepo))[AssessorViewModel::class.java]
        binding.syncBatch.setOnClickListener(this)


        val bundle = arguments
        if (bundle != null && !bundle.isEmpty) {
            batchId = bundle.getString(Constants.batchId).toString()
            batchNo = bundle.getString(Constants.batchNo).toString()

            binding.batchIdTv.text = batchId
            binding.batchNameTv.text = batchNo
        }


        bindUserListData()
    }

    private val chooseMainListener = object : ChooseStudentListListener {
        override fun chooseMemberAdapterListener(pos: Int, id: Int) {
            Utility.replaceFragment(StudentTakeAttendanceFragment(),mActivity.supportFragmentManager, binding.layoutRoot.id)
        }

    }

    override fun onClick(p0: View?) {
        when (p0){
            binding.syncBatch -> {
                mainViewModel.getBatchConfigByBatchId(batchId).observe(this){
                    batchConfigRes = it


                    saveBatchArrayData()
                    if (batchConfigRes != null)
                    {
                        // Sync Data


                        if (batchConfigRes.assessor_feedback.equals("1"))
                        {
                           Utility.replaceFragment(AssessorFeedbackFragment(), mActivity.supportFragmentManager, binding.layoutRoot.id)
                        }
                    }
                }


            }
        }
    }

    fun saveBatchData(syncBatchArray: SyncBatchArray) = runBlocking{
        val exists = assessorViewModel.isBatchIdExists(batchId)
        if (exists) {
            // The batch ID exists in the table
            assessorViewModel.updateBatchDetail(syncBatchArray)
        } else {
            assessorViewModel.saveBatchDetail(syncBatchArray)
        }

    }
    fun saveBatchArrayData()  = runBlocking{

        mainViewModel.getLoginData().observe(this@AssessorBatchListFragment) {
            val syncBatchArray =  SyncBatchArray()
            syncBatchArray.batch_id=batchId
            syncBatchArray.export_time=Utility.currentDateTime.toString()
            syncBatchArray.device_id=Build.ID
            syncBatchArray.exported_by=it.user_id
            syncBatchArray.exported_user_type=it.login_type
            syncBatchArray.export_type="complete"

            saveBatchData(syncBatchArray)
        }
    }

    private fun bindUserListData(){
        mainViewModel.getUserByBatchId(batchId).observe(viewLifecycleOwner){
            userList = it

            println(userList.toString())

           bindBatchListData()

        }
    }

    private fun bindBatchListData(){
        mainViewModel.getBatchByBatchId(batchId).observe(viewLifecycleOwner){
            batchRes = it

            println(batchRes.toString())

            studentListAdapter = AssessorBatchWiseAdapter(mActivity,mActivity.supportFragmentManager, userList, batchRes)
            binding.recyclerViewStudent.layoutManager = LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL,false)
            studentListAdapter.setAdapterListener(chooseMainListener)
            binding.recyclerViewStudent.adapter = studentListAdapter
        }
    }
}
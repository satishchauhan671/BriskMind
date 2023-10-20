package com.brisk.assessment.assessor.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.brisk.assessment.assessor.adapter.AssessorBatchWiseAdapter
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.BatchConfigDataHelper
import com.brisk.assessment.database.BatchDataHelper
import com.brisk.assessment.database.LoginDataHelper
import com.brisk.assessment.database.SyncBatchDataHelper
import com.brisk.assessment.database.UserArrayDataHelper
import com.brisk.assessment.databinding.FragmentAssessorListBatchWiseBinding
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.BatchConfigRes
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SyncBatchArray
import com.brisk.assessment.model.UserResponse
import kotlinx.coroutines.runBlocking

class AssessorBatchListFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentAssessorListBatchWiseBinding? = null
    private val binding get() = _binding!!
    private lateinit var studentListAdapter: AssessorBatchWiseAdapter
    private lateinit var mActivity: FragmentActivity
    var batchId = ""
    var batchNo = ""
    private lateinit var batchConfigRes: BatchConfigRes
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

        // initialize model factory
        binding.syncBatch.setOnClickListener(this)


        val bundle = arguments
        if (bundle != null && !bundle.isEmpty) {
            batchId = bundle.getString(Constants.batchId).toString()
            batchNo = bundle.getString(Constants.batchNo).toString()

            binding.batchIdTv.text = batchId
            binding.batchNameTv.text = batchNo
        }


        bindUserListData(batchId)
    }

    private val chooseMainListener = object : ChooseStudentListListener {
        override fun chooseMemberAdapterListener(pos: Int, id: Int) {
            val bundle = Bundle()
            bundle.putString(Constants.batchId, batchId)
            Utility.replaceFragmentWithBundle(
                StudentTakeAttendanceFragment(),
                mActivity.supportFragmentManager,
                binding.layoutRoot.id,
                bundle
            )
        }

    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.syncBatch -> {
                saveBatchArrayData()
                batchConfigRes =
                    BatchConfigDataHelper.getBatchConfigDataByBatchId(batchId, mActivity)!!
                if (batchConfigRes != null) {
                    if (batchConfigRes.assessor_feedback.equals("1")) {
                        val bundle = Bundle()
                        bundle.putString(Constants.batchId, batchId)

                        Utility.replaceFragmentWithBundle(
                            AssessorFeedbackFragment(),
                            mActivity.supportFragmentManager,
                            binding.layoutRoot.id,
                            bundle
                        )
                    }
                }
            }
        }
    }

    private fun saveBatchArrayData() {
        val loginRes: LoginRes? = LoginDataHelper.getLogin(mActivity)
        if (loginRes != null) {
            val syncBatchArray = SyncBatchArray()
            syncBatchArray.batch_id = batchId
            syncBatchArray.export_time = Utility.currentDateTime.toString()
            syncBatchArray.device_id = Build.ID
            syncBatchArray.exported_by = loginRes.user_id
            syncBatchArray.exported_user_type = loginRes.login_type
            syncBatchArray.export_type = "complete"

            SyncBatchDataHelper.syncBatchData(syncBatchArray, mActivity)
        }


    }

    fun saveBatchData(syncBatchArray: SyncBatchArray) = runBlocking {
        /* val exists = assessorViewModel.isBatchIdExists(batchId)
         if (exists) {
             // The batch ID exists in the table
             assessorViewModel.updateBatchDetail(syncBatchArray)
         } else {
             assessorViewModel.saveBatchDetail(syncBatchArray)
         }*/

    }

    /*   fun saveBatchArrayData()  = runBlocking{

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
   */
    private fun bindUserListData(batchId: String) {

        val userResponse: ArrayList<UserResponse>? =
            UserArrayDataHelper.getUserArrayDataByBatchId(batchId, mActivity)
        val batchRes: BatchRes? = BatchDataHelper.getBatchByBatchId(batchId, mActivity)

        if (userResponse != null && batchRes != null) {
            studentListAdapter = AssessorBatchWiseAdapter(
                mActivity,
                mActivity.supportFragmentManager,
                userResponse,
                batchRes
            )
            binding.recyclerViewStudent.layoutManager = LinearLayoutManager(
                mActivity,
                LinearLayoutManager.VERTICAL, false
            )
            studentListAdapter.setAdapterListener(chooseMainListener)
            binding.recyclerViewStudent.adapter = studentListAdapter
        }
    }
}
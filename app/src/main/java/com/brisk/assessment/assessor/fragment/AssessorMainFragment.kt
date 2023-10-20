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
import com.brisk.assessment.assessor.adapter.AssessorMainAdapter
import com.brisk.assessment.assessor.listener.BatchImageListener
import com.brisk.assessment.assessor.listener.ChooseAssessorMainListener
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.BatchDataHelper
import com.brisk.assessment.database.LoginDataHelper
import com.brisk.assessment.databinding.FragmentAssessorMainBinding
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.LoginRes

class AssessorMainFragment : Fragment() {

    private var _binding: FragmentAssessorMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var assessorMainAdapter: AssessorMainAdapter
    private lateinit var mActivity: FragmentActivity
    private lateinit var loginRes: LoginRes
    private lateinit var batchRes: List<BatchRes>
    private var userId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssessorMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLoginData()
        bindBatchData()

    }

    private val chooseMainListener = object : ChooseAssessorMainListener {
        override fun chooseMemberAdapterListener(pos: Int, batchNo: String, batchId : String) {
            val bundle = Bundle()
            bundle.putString(Constants.batchId, batchId)
            bundle.putString(Constants.batchNo, batchNo)
            bundle.putString(Constants.userId, userId)
            Utility.replaceFragmentWithBundle(
                AssessorIdProfileImageFragment(),
                mActivity.supportFragmentManager,
                binding.layoutRoot.id,
                bundle
            )
        }

    }

    private val batchImageListener = object : BatchImageListener {
        override fun batchImageAdapterListener(pos: Int, id: Int, visible: Boolean) {

            if (id == 0) {
                if (visible) {
                    binding.dimOverlay.root.visibility = View.VISIBLE
                } else {
                    binding.dimOverlay.root.visibility = View.GONE
                }
            } else {
                Utility.replaceFragment(
                    AssessorBatchImagesFragment(),
                    mActivity.supportFragmentManager,
                    binding.layoutRoot.id
                )
            }
        }
    }

    private fun getLoginData() {
        val loginRes : LoginRes? =LoginDataHelper.getLogin(mActivity)
        if (loginRes!=null)
        {
            binding.assessorNameTv.text = loginRes.user_name
            binding.assessorIdTv.text = loginRes.user_id
            userId = loginRes.user_id?:""
        }

    }

    private fun bindBatchData(){

        val batchRes : ArrayList<BatchRes>? = BatchDataHelper.getBatchData(mActivity)

        if (batchRes !=null)
        {
            assessorMainAdapter = AssessorMainAdapter(mActivity, mActivity.supportFragmentManager, batchRes)
            binding.assessorTestRv.layoutManager =
                LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
            assessorMainAdapter.setAdapterListener(chooseMainListener)
            assessorMainAdapter.setBatchImageAdapterListener(batchImageListener)
            binding.assessorTestRv.adapter = assessorMainAdapter
        }

    }

}
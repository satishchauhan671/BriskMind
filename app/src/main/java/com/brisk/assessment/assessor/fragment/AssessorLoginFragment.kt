package com.brisk.assessment.assessor.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.brisk.assessment.BriskMindApplication.Companion.appPackageName
import com.brisk.assessment.BriskMindApplication.Companion.deviceId
import com.brisk.assessment.BriskMindApplication.Companion.versionName
import com.brisk.assessment.R
import com.brisk.assessment.common.NetworkResult
import com.brisk.assessment.common.Utility
import com.brisk.assessment.common.Utility.showSnackBar
import com.brisk.assessment.databinding.AssessorLoginLayoutBinding
import com.brisk.assessment.fragments.StudentImagesFragments
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.repositories.LoginRepo
import com.brisk.assessment.viewmodels.MainViewModel
import com.brisk.assessment.viewmodels.MainViewModelFactory

class AssessorLoginFragment : Fragment(), View.OnClickListener {

    private var loginAs: String = ""
    private var _binding: AssessorLoginLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var dialog: Dialog
    private var loginId : String? = ""
    private var password : String? = ""
    private lateinit var repo : LoginRepo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AssessorLoginLayoutBinding.inflate(inflater, container, false)
        binding.txtSignInAssessor.setOnClickListener(this)
        binding.signInLayAssessor.setOnClickListener(this)

        setLoginAs()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = Utility.progressDialog(mActivity)
        repo = LoginRepo(mActivity.application)
        mainViewModelFactory = MainViewModelFactory(repo)
        mainViewModel = ViewModelProvider(mActivity,mainViewModelFactory)[MainViewModel::class.java]
        MainViewModel(repo)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.txtSignInAssessor,
            binding.signInLayAssessor -> {
                when (loginAs) {
                    "Candidate" -> {
                        loginId = binding.etLoginId.text.toString()
                        password = binding.edtPassword.text.toString()
                        val validateResult = mainViewModel.isLoginValidRequest(loginId, password)
                        if (validateResult.first) {
                            val loginReq = getLoginRequest()
                            mainViewModel = ViewModelProvider(mActivity,MainViewModelFactory(repo))[MainViewModel::class.java]
                            bindObservers()
                            mainViewModel.login(loginReq)
                        } else {
                            showValidationErrors(validateResult.second)
                        }
                    }

                    "Assessor" -> {
                        Utility.replaceFragment(
                            AssessorLoginImageFragment(),
                            mActivity.supportFragmentManager,
                            R.id.layout_root
                        )
                    }

                    else -> {
                        Toast.makeText(mActivity, "Please Select Login Type", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun showValidationErrors(error: String) {
        showSnackBar(binding.root, error)
    }

    private fun setLoginAs() {
        val extraCableList = ArrayList<String>()
        extraCableList.add("Select")
        extraCableList.add("Candidate")
        extraCableList.add("Assessor")

        val adapter = ArrayAdapter(
            mActivity!!,
            R.layout.spinner_item_type, extraCableList
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_type_list)
        binding.spinnerLoginAs.adapter = adapter

        binding.spinnerLoginAs.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    loginAs =
                        binding.spinnerLoginAs.selectedItem.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
    }


    private fun bindObservers() {
        mainViewModel.loginRes.observe(viewLifecycleOwner, Observer {
            dialog.show()
            when (it) {
                is NetworkResult.Success -> {
                   dialog.dismiss()
                    if (it.data != null) {
                       if (it.data.status.equals("success", ignoreCase = true)){
                           Utility.replaceFragment(
                               StudentImagesFragments("Start"),
                               mActivity.supportFragmentManager,
                               R.id.layout_root
                           )
                       }else{
                           showValidationErrors(it.data.message ?: "Something Went Wrong!")
                       }
                    }
                }

                is NetworkResult.Error -> {
                    // binding.progressBar.isVisible = false
                    dialog.dismiss()
                    showValidationErrors(it.message.toString())
                }

                is NetworkResult.Loading -> {
                    // binding.progressBar.isVisible = true
                    if (!dialog.isShowing) {
                        dialog.show()
                    }
                }
            }
        })
    }


    private fun getLoginRequest(): LoginReq {
        return binding.run {
            LoginReq(
                Utility.stringToBase64(deviceId),
                Utility.stringToBase64(password!!.trim()),
                Utility.stringToBase64(loginId!!.trim()),
                Utility.stringToBase64(versionName),
                Utility.stringToBase64(appPackageName)
            )
        }
    }
}
package com.brisk.assessment.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.brisk.assessment.BriskMindApplication.Companion.appPackageName
import com.brisk.assessment.BriskMindApplication.Companion.deviceId
import com.brisk.assessment.BriskMindApplication.Companion.versionName
import com.brisk.assessment.R
import com.brisk.assessment.assessor.activity.AssessorActivityMain
import com.brisk.assessment.common.NetworkResult
import com.brisk.assessment.common.Utility
import com.brisk.assessment.common.Utility.showSnackBar
import com.brisk.assessment.databinding.AssessorLoginLayoutBinding
import com.brisk.assessment.model.ImportAssessmentReq
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.repositories.LoginRepo
import com.brisk.assessment.viewmodels.MainViewModel
import com.brisk.assessment.viewmodels.MainViewModelFactory

class LoginFragment : Fragment(), View.OnClickListener {

    private var loginAs: String = ""
    private var _binding: AssessorLoginLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var dialog: Dialog
    private var loginId: String? = ""
    private var password: String? = ""
    private var loginType: String? = ""
    private var userId: String? = ""
    private lateinit var repo: LoginRepo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AssessorLoginLayoutBinding.inflate(inflater, container, false)
        binding.txtSignInAssessor.setOnClickListener(this)
        binding.signInLayAssessor.setOnClickListener(this)

        setLoginAs()

        return binding.layoutRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = Utility.progressDialog(mActivity)
        repo = LoginRepo(mActivity.application)
        mainViewModelFactory = MainViewModelFactory(repo)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
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
                if (!TextUtils.isEmpty(loginAs) && !loginAs.equals("Select", ignoreCase = true)) {
                    loginId = binding.etLoginId.text.toString()
                    password = binding.edtPassword.text.toString()
                    val validateResult = mainViewModel.isLoginValidRequest(loginId, password)
                    if (validateResult.first) {
                        val loginReq = getLoginRequest()
                        mainViewModel = ViewModelProvider(
                            mActivity,
                            MainViewModelFactory(repo)
                        )[MainViewModel::class.java]
                        bindObservers()
                        mainViewModel.login(loginReq)
                    } else {
                        showMessage(validateResult.second)
                    }
                } else {
                    showMessage("Please Select Login As")
                }
            }
        }
    }

    private fun showMessage(error: String) {
        showSnackBar(binding.layoutRoot, error)
    }

    private fun setLoginAs() {
        val extraCableList = ArrayList<String>()
        extraCableList.add("Select")
        extraCableList.add("Candidate")
        extraCableList.add("Assessor")

        val adapter = ArrayAdapter(
            mActivity,
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
        mainViewModel.loginRes.observe(viewLifecycleOwner) {
            dialog.show()
            when (it) {
                is NetworkResult.Success -> {
                    dialog.dismiss()
                    if (it.data != null) {
                        if (it.data.status.equals("success", ignoreCase = true)) {

                            userId = it.data.user_id
                            loginType = it.data.login_type

                            if (!Utility.isNullOrEmpty(userId)) {
                                val impAssessmentReq =
                                    getImportAssessmentRequest(userId!!, loginType!!)
                                bindImportObserver()
                                mainViewModel.importAssessmentData(impAssessmentReq)
                            }

                            showMessage(it.data.message ?: "Login Successfully.")
                        } else {
                            showMessage(it.data.message ?: "Something Went Wrong!")
                        }
                    }
                }

                is NetworkResult.Error -> {
                    dialog.dismiss()
                    showMessage(it.message.toString())
                }

                is NetworkResult.Loading -> {
                    if (!dialog.isShowing) {
                        dialog.show()
                    }
                }

                else -> {}
            }
        }
    }

    private fun bindImportObserver() {
        mainViewModel.importAssessmentRes.observe(viewLifecycleOwner) {
            dialog.show()
            when (it) {
                is NetworkResult.Success -> {
                    dialog.dismiss()
                    if (it.data != null) {
                        when (loginAs) {
                            "Candidate" -> {
                                Utility.replaceFragment(
                                    StudentImagesFragments("Start"),
                                    mActivity.supportFragmentManager,
                                    R.id.layout_root
                                )
                            }

                            "Assessor" -> {
                                /*Utility.replaceFragment(
                                    AssessorLoginImageFragment(),
                                    mActivity.supportFragmentManager,
                                    R.id.layout_root
                                )*/
                                val intent = Intent(context, AssessorActivityMain::class.java)
                                startActivity(intent)
                            }
                        }

                    }
                }

                is NetworkResult.Error -> {
                    dialog.dismiss()
                    showMessage(it.message.toString())
                }

                is NetworkResult.Loading -> {
                    if (!dialog.isShowing) {
                        dialog.show()
                    }
                }
            }

        }
    }

    private fun getLoginRequest(): LoginReq {
        return binding.run {
            LoginReq(
                Utility.stringToBase64(deviceId),
                Utility.stringToBase64(password!!.trim()),
                Utility.stringToBase64(loginId!!.trim()),
                Utility.stringToBase64(versionName),
                appPackageName,
                loginAs
            )
        }
    }


    private fun getImportAssessmentRequest(userId: String, loginType: String): ImportAssessmentReq {
        return binding.run {
            ImportAssessmentReq(
                Utility.stringToBase64(userId),
                Utility.stringToBase64(deviceId),
                Utility.stringToBase64(loginType),
                Utility.stringToBase64(versionName)
            )
        }
    }
}
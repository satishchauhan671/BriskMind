package com.brisk.assessment.assessor.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.brisk.assessment.BriskMindApplication
import com.brisk.assessment.BuildConfig
import com.brisk.assessment.R
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.AssessorLoginLayoutBinding
import com.brisk.assessment.fragments.StudentImagesFragments
import com.brisk.assessment.model.LoginReq
import com.brisk.assessment.viewmodels.MainViewModel
import com.brisk.assessment.viewmodels.MainViewModelFactory

class AssessorLoginFragment : Fragment(), View.OnClickListener {

    private var loginAs: String = ""
    private var _binding: AssessorLoginLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity
    private lateinit var mainViewModel: MainViewModel
    lateinit var mainViewModelFactory: MainViewModelFactory

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
        try {
            val packageName: String = mActivity.packageName
            val versionName: String = BuildConfig.VERSION_NAME // Build version name
            // Log the version code and version name
            Log.d("AppInfo", "Version Name: $versionName")

            val loginReq = LoginReq(Utility.stringToBase64(Build.DEVICE),Utility.stringToBase64("f1r9Lg"),Utility.stringToBase64("080821"),
                Utility.stringToBase64(versionName),Utility.stringToBase64(packageName))
            val repo = (mActivity.application as BriskMindApplication).loginRepo
            mainViewModelFactory = MainViewModelFactory(repo,loginReq)
            mainViewModel = ViewModelProvider(mActivity,mainViewModelFactory)[MainViewModel::class.java]

            mainViewModel.loginRes.observe(mActivity) {
                Log.d("TAG", "onViewCreated: $it")
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

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
                        Utility.replaceFragment(
                            StudentImagesFragments("Start"),
                            mActivity.supportFragmentManager,
                            R.id.layout_root
                        )
                    }
                    "Assessor" -> {
                        Utility.replaceFragment(
                            AssessorLoginImageFragment(),
                            mActivity.supportFragmentManager,
                            R.id.layout_root
                        )
                    }
                    else -> {
                        Toast.makeText(mActivity, "Please Select Login Type", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
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

}
package com.briskmind.assessment.assessor.fragment

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
import com.briskmind.assessment.R
import com.briskmind.assessment.common.Utility
import com.briskmind.assessment.databinding.AssessorLoginLayoutBinding
import com.briskmind.assessment.fragments.StudentImagesFragments

class AssessorLoginFragment : Fragment(), View.OnClickListener {

    private var loginAs: String = ""
    private var _binding: AssessorLoginLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var mActivity: FragmentActivity

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.txtSignInAssessor,
            binding.signInLayAssessor -> {
                if (loginAs.equals("Candidate")) {
                    Utility.replaceFragment(
                        StudentImagesFragments("Start"),
                        mActivity.supportFragmentManager,
                        R.id.layout_root
                    )
                } else if (loginAs.equals("Assessor")) {
                    Utility.replaceFragment(
                        AssessorLoginImageFragment(),
                        mActivity.supportFragmentManager,
                        R.id.layout_root
                    )
                } else {
                    Toast.makeText(mActivity, "Please Select Login Type", Toast.LENGTH_LONG).show()
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
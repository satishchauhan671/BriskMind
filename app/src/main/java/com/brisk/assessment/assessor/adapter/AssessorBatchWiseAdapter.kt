package com.brisk.assessment.assessor.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.brisk.assessment.R
import com.brisk.assessment.assessor.activity.AssessorTestActivity
import com.brisk.assessment.assessor.fragment.AssessorFeedbackFragment
import com.brisk.assessment.assessor.fragment.UserPracticalAttendanceFragment
import com.brisk.assessment.assessor.fragment.UserVivaAttendanceFragment
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.BatchConfigDataHelper
import com.brisk.assessment.databinding.StudentListBatchWiseAdapterBinding
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.BatchRes
import com.brisk.assessment.model.UserResponse

class AssessorBatchWiseAdapter(
    mContext: Context,
    fragmentManager: FragmentManager,
    userList: ArrayList<UserResponse>,
    batchRes: BatchRes
) :
    RecyclerView.Adapter<AssessorBatchWiseAdapter.ViewHolder>() {

    private val batchRes: BatchRes = batchRes
    private val mContext: Context = mContext
    private val fragmentManager: FragmentManager = fragmentManager
    private val userList: List<UserResponse> = userList
    private lateinit var chooseStudentListListener: ChooseStudentListListener
    var selectedPos: Int = -1
    var lastSelectedPos: Int = -1

    fun setAdapterListener(chooseStudentListListener: ChooseStudentListListener) {
        this.chooseStudentListListener = chooseStudentListListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StudentListBatchWiseAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(userList[position]) {
                binding.studentNameTv.text = this.user_name

                binding.attendanceBtn.setOnClickListener {
                    chooseStudentListListener.chooseMemberAdapterListener(position, 0)
                }

                binding.practicalBtn.setOnClickListener {
                    val batchConfigRes =
                        BatchConfigDataHelper.getBatchConfigDataByBatchId(
                            this.batch_id!!,
                            mContext
                        )!!
                    if (batchConfigRes != null) {
                        if (batchConfigRes.u_attendance_before_viva.equals("1")) {
                            val bundle = Bundle()
                            bundle.putString(Constants.paperSetId, this.viva_paper_set_id)
                            bundle.putString(Constants.batchId, this.batch_id)
                            bundle.putString(Constants.paperType, Constants.viva)
                            Utility.replaceFragmentWithBundle(
                                UserPracticalAttendanceFragment(),
                                fragmentManager,
                                R.id.layout_root,
                                bundle
                            )
                        } else {
                            val intent = Intent(mContext, AssessorTestActivity::class.java)
                            intent.putExtra(Constants.paperSetId, this.practical_paper_set_id)
                            intent.putExtra(Constants.batchId, this.batch_id)
                            intent.putExtra(Constants.paperType, Constants.practical)
                            mContext.startActivity(intent)
                        }
                    }

                  /*  val intent = Intent(mContext, AssessorTestActivity::class.java)
                    intent.putExtra(Constants.paperSetId, this.practical_paper_set_id)
                    intent.putExtra(Constants.batchId, this.batch_id)
                    intent.putExtra(Constants.paperType, Constants.practical)
                    mContext.startActivity(intent)*/
                }

                binding.vivaBtn.setOnClickListener {

                    val batchConfigRes =
                        BatchConfigDataHelper.getBatchConfigDataByBatchId(
                            this.batch_id!!,
                            mContext
                        )!!
                    if (batchConfigRes != null) {
                        if (batchConfigRes.u_attendance_before_viva.equals("1")) {
                            val bundle = Bundle()
                            bundle.putString(Constants.paperSetId, this.viva_paper_set_id)
                            bundle.putString(Constants.batchId, this.batch_id)
                            bundle.putString(Constants.paperType, Constants.viva)
                            Utility.replaceFragmentWithBundle(
                                UserVivaAttendanceFragment(),
                                fragmentManager,
                                R.id.layout_root,
                                bundle
                            )
                        } else {
                            val intent = Intent(mContext, AssessorTestActivity::class.java)
                            intent.putExtra(Constants.paperSetId, this.viva_paper_set_id)
                            intent.putExtra(Constants.batchId, this.batch_id)
                            intent.putExtra(Constants.paperType, Constants.viva)
                            mContext.startActivity(intent)
                        }
                    }

                }

                if (!this.candidate_id.isNullOrEmpty()) {
                    binding.enrollmentTv.text = this.candidate_id
                }
                if (!this.enrollment_no.isNullOrEmpty()) {
                    binding.enrollmentTv.text = this.enrollment_no
                }

                if ((batchRes.total_viva_marks?.toDouble() ?: 0.0) > 0) {
                    binding.vivaBtn.isEnabled = true
                    binding.vivaBtn.isClickable = true
                    binding.vivaAttendanceBtn.visibility = View.VISIBLE

                    handleButtonName(
                        this.viva_paper_set_id ?: "",
                        "",
                        binding.vivaTv,
                        binding.vivaAttendanceBtn,
                        binding.vivaBtn
                    )
                } else {
                    binding.vivaBtn.isEnabled = false
                    binding.vivaBtn.isClickable = false
                    binding.vivaAttendanceBtn.visibility = View.GONE
                    binding.vivaTv.text = Constants.notApplicable
                }

                if ((batchRes.total_practical_marks?.toDouble() ?: 0.0) > 0) {
                    binding.practicalBtn.isEnabled = true
                    binding.practicalBtn.isClickable = true
                    binding.practicalAttendanceBtn.visibility = View.VISIBLE

                    handleButtonName(
                        this.practical_paper_set_id ?: "",
                        "",
                        binding.practicalTv,
                        binding.practicalAttendanceBtn,
                        binding.practicalBtn
                    )

                } else {
                    binding.practicalBtn.isEnabled = false
                    binding.practicalBtn.isClickable = false
                    binding.practicalAttendanceBtn.visibility = View.GONE
                    binding.practicalTv.text = Constants.notApplicable
                }


                binding.vivaAttendanceBtn.setOnClickListener {
                    showAlert("Are you want to mark absent?", Constants.viva).show()
                }

                binding.practicalAttendanceBtn.setOnClickListener {
                    showAlert("Are you want to mark absent?", Constants.practical).show()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class ViewHolder(val binding: StudentListBatchWiseAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun removeAt(position: Int) {
        notifyDataSetChanged()
    }

    private fun showAlert(message: String, from: String): AlertDialog.Builder {
        val dialog = AlertDialog.Builder(mContext)
        dialog.setMessage(message)
        dialog.setIcon(R.mipmap.briskmind_icon)
        dialog.setPositiveButton(Constants.yes) { dialog, which ->
            dialog.dismiss()
        }
        dialog.setNegativeButton(Constants.no) { dialog, which ->
            dialog.dismiss()
        }
        return dialog
    }

    private fun handleButtonName(
        id: String,
        status: String,
        statusTv: TextView,
        attendanceTv: LinearLayout,
        statusBtn: LinearLayout
    ): String {
        var name = ""
        if (id == "0") {
            name = Constants.notApplicable
            statusTv.text = name
            statusBtn.isClickable = false
            statusBtn.isEnabled = false
            attendanceTv.visibility = View.GONE
        } else if (status == Constants.done) {
            name = Constants.done
            statusTv.text = name
            statusBtn.isClickable = false
            statusBtn.isEnabled = false
            attendanceTv.visibility = View.GONE
        } else if (status == Constants.inProgress) {
            name = Constants.inProgress
            statusTv.text = name
            statusBtn.isClickable = true
            statusBtn.isEnabled = true
            attendanceTv.visibility = View.GONE
        } else if (status == Constants.start) {
            name = Constants.start
            statusTv.text = name
            statusBtn.isClickable = true
            statusBtn.isEnabled = true
            attendanceTv.visibility = View.VISIBLE
        } else {
            name = Constants.start
            statusTv.text = name
            statusBtn.isClickable = true
            statusBtn.isEnabled = true
            attendanceTv.visibility = View.VISIBLE
        }
        return name
    }
}
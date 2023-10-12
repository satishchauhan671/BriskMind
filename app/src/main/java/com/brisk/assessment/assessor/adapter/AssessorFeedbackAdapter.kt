package com.brisk.assessment.assessor.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.brisk.assessment.R
import com.brisk.assessment.assessor.activity.AssessorTestActivity
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.databinding.FeedbackAdapterLayoutBinding
import com.brisk.assessment.databinding.StudentListBatchWiseAdapterBinding
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.UserResponse

class AssessorFeedbackAdapter(
    mContext: Context,
    fragmentManager: FragmentManager,
    feedbackResponse: List<FeedbackResponse>
) :
    RecyclerView.Adapter<AssessorFeedbackAdapter.ViewHolder>() {

    private val mContext: Context = mContext
    private val fragmentManager: FragmentManager = fragmentManager
    private lateinit var chooseStudentListListener: ChooseStudentListListener
    private val feedbackResponse: List<FeedbackResponse> = feedbackResponse

    fun setAdapterListener(chooseStudentListListener: ChooseStudentListListener) {
        this.chooseStudentListListener = chooseStudentListListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            FeedbackAdapterLayoutBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder)
        {
            with(feedbackResponse[position]) {

                if (!Utility.isNullOrEmpty(this.fq_text)) {
                    binding.txtFeedbackQuestion.text = "Question :- " + this.fq_text
                }
                if (!Utility.isNullOrEmpty(this.fq_type)) {
                    if (this.fq_type.equals("text")) {
                        binding.lytEdittextFeedback.visibility = View.VISIBLE
                        binding.radioGroupFeedback.visibility = View.GONE
                    } else {
                        binding.lytEdittextFeedback.visibility = View.GONE
                        binding.radioGroupFeedback.visibility = View.VISIBLE
                        binding.radioGroupFeedback.removeAllViews()
                        if (!Utility.isNullOrEmpty(this.option1))
                        {
                            val radioButton = RadioButton(mContext)
                            radioButton.text = this.option1
                            radioButton.id = View.generateViewId()
                            binding.radioGroupFeedback.addView(radioButton)
                        }
                        if (!Utility.isNullOrEmpty(this.option2))
                        {
                            val radioButton = RadioButton(mContext)
                            radioButton.text = this.option2
                            radioButton.id = View.generateViewId()
                            binding.radioGroupFeedback.addView(radioButton)
                        }
                        if (!Utility.isNullOrEmpty(this.option3))
                        {
                            val radioButton = RadioButton(mContext)
                            radioButton.text = this.option3
                            radioButton.id = View.generateViewId()
                            binding.radioGroupFeedback.addView(radioButton)
                        }
                        if (!Utility.isNullOrEmpty(this.option4))
                        {
                            val radioButton = RadioButton(mContext)
                            radioButton.text = this.option4
                            radioButton.id = View.generateViewId()
                            binding.radioGroupFeedback.addView(radioButton)
                        }
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return feedbackResponse.size
    }


    class ViewHolder(val binding: FeedbackAdapterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun removeAt(position: Int) {
        //   userMemberList.removeAt(position)
        notifyDataSetChanged()
    }
}
package com.brisk.assessment.assessor.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.brisk.assessment.common.Constants
import com.brisk.assessment.common.Utility
import com.brisk.assessment.database.LoginDataHelper
import com.brisk.assessment.database.SyncFeedbackDataHelper
import com.brisk.assessment.databinding.FeedbackAdapterLayoutBinding
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.FeedbackResponse
import com.brisk.assessment.model.LoginRes
import com.brisk.assessment.model.SyncFeedbackArray


class AssessorFeedbackAdapter(
    mContext: Context,
    fragmentManager: FragmentManager,
    feedbackResponse: List<FeedbackResponse>,
    batchId: String
) :
    RecyclerView.Adapter<AssessorFeedbackAdapter.ViewHolder>() {

    private val mContext: Context = mContext
    private val fragmentManager: FragmentManager = fragmentManager
    private lateinit var chooseStudentListListener: ChooseStudentListListener
    private val feedbackResponse: List<FeedbackResponse> = feedbackResponse
    private val batchId = batchId

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
                    binding.txtFeedbackQuestion.text = Constants.questionNo + this.fq_text
                }
                if (!Utility.isNullOrEmpty(this.fq_type)) {
                    if (this.fq_type.equals("text")) {
                        binding.lytEdittextFeedback.visibility = View.VISIBLE
                        binding.radioGroupFeedback.visibility = View.GONE
                    } else {
                        binding.lytEdittextFeedback.visibility = View.GONE
                        binding.radioGroupFeedback.visibility = View.VISIBLE
                        binding.radioGroupFeedback.removeAllViews()
                        if (!Utility.isNullOrEmpty(this.option1)) {
                            val radioButton = RadioButton(mContext)
                            radioButton.text = this.option1
                            radioButton.id = View.generateViewId()
                            binding.radioGroupFeedback.addView(radioButton)
                        }
                        if (!Utility.isNullOrEmpty(this.option2)) {
                            val radioButton = RadioButton(mContext)
                            radioButton.text = this.option2
                            radioButton.id = View.generateViewId()
                            binding.radioGroupFeedback.addView(radioButton)
                        }
                        if (!Utility.isNullOrEmpty(this.option3)) {
                            val radioButton = RadioButton(mContext)
                            radioButton.text = this.option3
                            radioButton.id = View.generateViewId()
                            binding.radioGroupFeedback.addView(radioButton)
                        }
                        if (!Utility.isNullOrEmpty(this.option4)) {
                            val radioButton = RadioButton(mContext)
                            radioButton.text = this.option4
                            radioButton.id = View.generateViewId()
                            binding.radioGroupFeedback.addView(radioButton)
                        }
                    }
                }

                binding.radioGroupFeedback.setOnCheckedChangeListener { radioGroup, i ->
                    val radioButton = radioGroup.findViewById<View>(i)
                    val index = radioGroup.indexOfChild(radioButton)

                    saveSyncFeedbackQuestion(index, position, "")
                }

                binding.txtFeedbackEdit.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable) {
                        saveSyncFeedbackQuestion(0,position,s.toString())
                    }
                })
            }
        }

    }

    private fun saveSyncFeedbackQuestion(index: Int, position: Int, toString: String) {
        val loginRes: LoginRes? = LoginDataHelper.getLogin(mContext)
        if (loginRes != null) {
            val syncFeedbackArray = SyncFeedbackArray()
            syncFeedbackArray.feedback_q_id = feedbackResponse[position].fq_id
            if (feedbackResponse[position].fq_type.equals("text"))
            {
                syncFeedbackArray.response = toString
            }
            else
            {
                when (index) {
                    0 -> {
                        syncFeedbackArray.response = feedbackResponse[position].option1
                    }
                    1 -> {
                        syncFeedbackArray.response = feedbackResponse[position].option2
                    }
                    2 -> {
                        syncFeedbackArray.response = feedbackResponse[position].option3
                    }
                    3 -> {
                        syncFeedbackArray.response = feedbackResponse[position].option4
                    }
                }
            }
            syncFeedbackArray.batch_id = batchId
            syncFeedbackArray.user_id = loginRes.user_id
            syncFeedbackArray.user_type = loginRes.login_type
            syncFeedbackArray.created_at = Utility.currentDateTime

            SyncFeedbackDataHelper.syncFeedbackData(syncFeedbackArray, mContext)
        }
    }

    override fun getItemCount(): Int {
        return feedbackResponse.size
    }


    class ViewHolder(val binding: FeedbackAdapterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

}
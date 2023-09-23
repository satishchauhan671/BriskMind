package com.brisk.assessment.assessor.adapter

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.brisk.assessment.R
import com.brisk.assessment.databinding.AdapterPaperBinding
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.PaperResponse
import com.brisk.assessment.model.SubQuestionResponse
import com.brisk.assessment.repositories.LoginRepo
import com.brisk.assessment.viewmodels.MainViewModel
import com.brisk.assessment.viewmodels.MainViewModelFactory

class PaperAdapter(
    mContext: Context,
    fragmentManager: FragmentManager,
    paperList: List<SubQuestionResponse>,
    mainViewModel: MainViewModel
) :
    RecyclerView.Adapter<PaperAdapter.ViewHolder>() {
    private val mContext: Context = mContext
    private val fragmentManager: FragmentManager = fragmentManager
    private val paperList: List<SubQuestionResponse> = paperList
    private lateinit var chooseStudentListListener: ChooseStudentListListener
    private var mainViewModel: MainViewModel = mainViewModel
    fun setAdapterListener(chooseStudentListListener: ChooseStudentListListener) {
        this.chooseStudentListListener = chooseStudentListListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterPaperBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(paperList[position]) {

                if (!this.squestion_id.isNullOrEmpty()) {
                    bindOptionListData(this, binding)
                }


            }
        }
    }

    override fun getItemCount(): Int {
        return paperList.size
    }


    class ViewHolder(val binding: AdapterPaperBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun removeAt(position: Int) {
        //   userMemberList.removeAt(position)
        notifyDataSetChanged()
    }


    private fun bindOptionListData(subQuestion: SubQuestionResponse, binding: AdapterPaperBinding){
        mainViewModel.getOptionBySubQuestionId(subQuestion.squestion_id!!).observeForever{
            binding.radioGroup.removeAllViews() // Clear existing RadioButtons if any
            val optionList = it

            binding.queMarksTv.text = mContext.resources.getString(R.string.ques_mark, subQuestion.question_marks)

            binding.subQuestionTv.text = subQuestion.question

            if (optionList != null && optionList.isNotEmpty()) {
                for (item in optionList) {
                    val radioButton = RadioButton(binding.radioGroup.context)
                    radioButton.text = item.option // You can set the option text here

                    val layoutParams = RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                    )
                    radioButton.layoutParams = layoutParams

                    binding.radioGroup.addView(radioButton)
                }
            }
        }
    }

    private fun bindTransOptionListData(subQuestionId: String){
        mainViewModel.getOptionBySubQuestionId(subQuestionId).observeForever{

        }
    }

}
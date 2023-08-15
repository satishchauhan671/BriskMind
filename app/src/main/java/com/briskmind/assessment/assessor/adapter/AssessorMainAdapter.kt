package com.briskmind.assessment.assessor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.briskmind.assessment.R
import com.briskmind.assessment.assessor.listener.ChooseAssessorMainListener

class AssessorMainAdapter(mContext: Context,fragmentManager: FragmentManager) :
    RecyclerView.Adapter<AssessorMainAdapter.ViewHolder>() {

    private lateinit var chooseAssessorMainListener: ChooseAssessorMainListener

    fun setAdapterListener(chooseAssessorMainListener: ChooseAssessorMainListener){
        this.chooseAssessorMainListener = chooseAssessorMainListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.assessor_list_adapter_layout, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.startBatchAssessor.setOnClickListener{
            chooseAssessorMainListener.chooseMemberAdapterListener(position,0)
        }
    }

    override fun getItemCount(): Int {
        return 10
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view : View
        var dotsIv : ImageView
        var startBatchAssessor : LinearLayout
        init {
            view = itemView
            startBatchAssessor = itemView.findViewById(R.id.startBatchAssessor)
            dotsIv = itemView.findViewById(R.id.dotsIv)
        }
    }

    fun removeAt(position: Int) {
     //   userMemberList.removeAt(position)
        notifyDataSetChanged()
    }
}
package com.briskmind.assessment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.briskmind.assessment.R
import com.briskmind.assessment.assessor.listener.ChooseAssessorMainListener
import com.briskmind.assessment.listner.ChooseStudentListListener

class StudentTestPageNoAdapter(mContext: Context, fragmentManager: FragmentManager) :
    RecyclerView.Adapter<StudentTestPageNoAdapter.ViewHolder>() {

    private val mContext: Context = mContext
    private val fragmentManager: FragmentManager = fragmentManager
    private lateinit var chooseStudentMainListener: ChooseStudentListListener
    var selectedPos : Int = -1
    var lastSelectedPos : Int = -1

    fun setAdapterListener(chooseStudentMainListener: ChooseStudentListListener){
        this.chooseStudentMainListener = chooseStudentMainListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.student_page_no_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setOnClickListener{
            chooseStudentMainListener   .chooseMemberAdapterListener(position,0)
        }
        val plusOnePos = position + 1
        holder.pageNoTv.text = plusOnePos.toString()

        if (position < 3) {
            holder.pageNoTv.setBackgroundResource(R.drawable.circle_green)
            holder.pageNoTv.setTextColor(mContext.resources.getColor(R.color.white))
        }else if (position < 4){
            holder.pageNoTv.setBackgroundResource(R.drawable.circle_puple)
            holder.pageNoTv.setTextColor(mContext.resources.getColor(R.color.white))
        }else if (position < 6){
            holder.pageNoTv.setBackgroundResource(R.drawable.circle_black)
            holder.pageNoTv.setTextColor(mContext.resources.getColor(R.color.white))
        }else if (position < 9){
            holder.pageNoTv.setBackgroundResource(R.drawable.circle_blue)
            holder.pageNoTv.setTextColor(mContext.resources.getColor(R.color.white))
        }else{
            holder.pageNoTv.setBackgroundResource(R.drawable.circle_white)
            holder.pageNoTv.setTextColor(mContext.resources.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int {
        return 11
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view : View
        var pageNoTv : TextView
        init {
            view = itemView
            pageNoTv = itemView.findViewById(R.id.pageNoTv)
        }


    }

    fun removeAt(position: Int) {
     //   userMemberList.removeAt(position)
        notifyDataSetChanged()
    }
}
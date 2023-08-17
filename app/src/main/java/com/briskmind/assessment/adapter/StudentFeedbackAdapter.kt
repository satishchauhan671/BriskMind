package com.briskmind.assessment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.briskmind.assessment.R
import com.briskmind.assessment.listner.ChooseStudentListListener

class StudentFeedbackAdapter(mContext: Context, fragmentManager: FragmentManager) :
    RecyclerView.Adapter<StudentFeedbackAdapter.ViewHolder>() {

    private val mContext: Context = mContext
    private val fragmentManager: FragmentManager = fragmentManager
    private lateinit var chooseStudentListListener: ChooseStudentListListener
    var selectedPos : Int = -1
    var lastSelectedPos : Int = -1

    fun setAdapterListener(chooseStudentListListener: ChooseStudentListListener){
        this.chooseStudentListListener = chooseStudentListListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.feedback_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view : View
        init {
            view = itemView
        }
    }

    fun removeAt(position: Int) {
     //   userMemberList.removeAt(position)
        notifyDataSetChanged()
    }
}
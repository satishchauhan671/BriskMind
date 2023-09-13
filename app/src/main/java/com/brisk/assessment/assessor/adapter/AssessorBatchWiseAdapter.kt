package com.brisk.assessment.assessor.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.brisk.assessment.R
import com.brisk.assessment.assessor.activity.AssessorTestActivity
import com.brisk.assessment.listner.ChooseStudentListListener
import com.brisk.assessment.model.UserResponse

class AssessorBatchWiseAdapter(mContext: Context, fragmentManager: FragmentManager, userList : List<UserResponse>) :
    RecyclerView.Adapter<AssessorBatchWiseAdapter.ViewHolder>() {

    private val mContext: Context = mContext
    private val fragmentManager: FragmentManager = fragmentManager
    private val userList : List<UserResponse> = userList
    private lateinit var chooseStudentListListener: ChooseStudentListListener
    var selectedPos : Int = -1
    var lastSelectedPos : Int = -1

    fun setAdapterListener(chooseStudentListListener: ChooseStudentListListener){
        this.chooseStudentListListener = chooseStudentListListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.student_list_batch_wise_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = userList[position]

        holder.studentNameTv.text = user.user_name

       holder.attendanceBtn.setOnClickListener{
           chooseStudentListListener.chooseMemberAdapterListener(position,0)
       }

        holder.practicalBtn.setOnClickListener{
            val intent = Intent(mContext,AssessorTestActivity::class.java)
            mContext.startActivity(intent)
        }

        holder.theoryBtn.setOnClickListener{
            val intent = Intent(mContext,AssessorTestActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view : View
        var practicalBtn : LinearLayout
        var theoryBtn : LinearLayout
        var attendanceBtn : LinearLayout
        var studentNameTv : TextView
        init {
            view = itemView
            practicalBtn = itemView.findViewById(R.id.practicalBtn)
            theoryBtn = itemView.findViewById(R.id.theoryBtn)
            attendanceBtn = itemView.findViewById(R.id.attendanceBtn)
            studentNameTv = itemView.findViewById(R.id.studentNameTv)
        }
    }

    fun removeAt(position: Int) {
     //   userMemberList.removeAt(position)
        notifyDataSetChanged()
    }
}
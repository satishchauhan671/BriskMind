package com.brisk.assessment.assessor.adapter

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.brisk.assessment.R
import com.brisk.assessment.assessor.listener.BatchImageListener
import com.brisk.assessment.assessor.listener.ChooseAssessorMainListener

class AssessorMainAdapter(mContext: Context, fragmentManager: FragmentManager) :
    RecyclerView.Adapter<AssessorMainAdapter.ViewHolder>() {

    private val mContext: Context = mContext
    private val fragmentManager: FragmentManager = fragmentManager
    private lateinit var chooseAssessorMainListener: ChooseAssessorMainListener
    private lateinit var batchImageListener: BatchImageListener
    var selectedPos: Int = -1
    var lastSelectedPos: Int = -1

    fun setAdapterListener(chooseAssessorMainListener: ChooseAssessorMainListener) {
        this.chooseAssessorMainListener = chooseAssessorMainListener
    }

    fun setBatchImageAdapterListener(batchImageListener: BatchImageListener) {
        this.batchImageListener = batchImageListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.assessor_list_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.startBatchAssessor.setOnClickListener {
            chooseAssessorMainListener.chooseMemberAdapterListener(position, 0)
        }

        holder.dotsIv.setOnClickListener {
            val popupView = LayoutInflater.from(mContext).inflate(R.layout.batch_img_pop_up, null)
            val popupWindow = PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true)

            val dotsIv = holder.dotsIv

            val location = IntArray(2)
            dotsIv.getLocationInWindow(location)
            val x = location[0]
            val y = location[1]
            val screenWidth = Resources.getSystem().displayMetrics.widthPixels // Get the screen width

            // Calculate the margin from the right side of the screen
            val marginFromRight = mContext.resources.getDimensionPixelOffset(R.dimen.margin_from_right) // Replace with your dimension resource
            // Calculate the x coordinate with the margin from the right
            val adjustedX = screenWidth - (x + dotsIv.width / 2) - marginFromRight
            popupWindow.showAtLocation(dotsIv, Gravity.TOP or Gravity.END, adjustedX, y + dotsIv.height)

            popupView.setOnClickListener{
                popupWindow.dismiss()
                batchImageListener.batchImageAdapterListener(position,1, false)
            }

            popupWindow.setOnDismissListener {
                batchImageListener.batchImageAdapterListener(position, 0, false)
            }
            if (popupWindow.isShowing) {
                batchImageListener.batchImageAdapterListener(position, 0, true)
            }
        }

    }

    override fun getItemCount(): Int {
        return 10
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view: View
        var dotsIv: ImageView
        var startBatchAssessor: LinearLayout
        var batchImgCard: CardView

        init {
            view = itemView
            startBatchAssessor = itemView.findViewById(R.id.startBatchAssessor)
            dotsIv = itemView.findViewById(R.id.dotsIv)
            batchImgCard = itemView.findViewById(R.id.batchImgCard)
        }
    }

    fun removeAt(position: Int) {
        //   userMemberList.removeAt(position)
        notifyDataSetChanged()
    }

}
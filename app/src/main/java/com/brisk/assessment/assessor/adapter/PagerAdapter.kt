package com.brisk.assessment.assessor.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brisk.assessment.assessor.activity.AssessorTestActivity
import com.brisk.assessment.fragments.PaperFragment
import com.brisk.assessment.model.PaperResponse

class PagerAdapter(fa: FragmentActivity, private val paperList : List<PaperResponse>,
                   private val assessorTestActivity: AssessorTestActivity
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = paperList.size

    override fun createFragment(position: Int): Fragment = PaperFragment(assessorTestActivity)

}